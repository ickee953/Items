/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.web.handler;

import com.mongodb.DuplicateKeyException;
import com.online.items.core.domain.AbstractDocument;
import com.online.items.core.domain.Item;
import com.online.items.core.domain.ItemCategory;
import com.online.items.core.domain.Rating;
import com.online.items.core.service.FileService;
import com.online.items.core.service.ItemCategoryService;
import com.online.items.core.service.ItemService;
import com.online.items.core.utils.BindingError;
import com.online.items.core.web.exception.ItemCreationException;
import com.online.items.core.web.exception.UnknownIdentifierException;
import com.online.items.core.web.model.ItemDetailsModel;
import com.online.items.core.web.model.ItemListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemWebHandler {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @RequestMapping
    public ModelAndView getView(){
        ModelAndView view = new ModelAndView("personal/items");

        List<Item> items = itemService.readAll();

        Map<ItemCategory, List<Item>> categoryItems = new HashMap<>();

        List<ItemCategory> categoriesSet = itemCategoryService.readAll();

        categoriesSet.forEach(cs -> {
            List<Item> filteredItemList = items.stream().filter(
                    i -> cs.equals(i.getCategory())
            ).collect(Collectors.toList());

            categoryItems.put( cs, filteredItemList );
        });

        view.addObject("categoryItems", categoryItems);

        return view;
    }

    /**
     * Return list of all available items
     *
     * @return Item list
     */
    @RequestMapping(
            value = "/list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ItemListModel>> list(){
        List<Item> items = itemService.readAll();

        List<ItemListModel> ret = items.stream().map(
                item -> new ItemListModel( item )
        ).collect(Collectors.toList());

        return new ResponseEntity<>( ret, HttpStatus.OK );
    }

    /**
     * Return item details by id
     *
     * @return Item
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ItemDetailsModel> details(
            @PathVariable( value = "id" ) String id
    ) throws UnknownIdentifierException {
        Optional<Item> o = itemService.readById( id );

        if( o.isPresent() ) {
            ItemDetailsModel ret = new ItemDetailsModel( o.get() );

            return new ResponseEntity<>( ret, HttpStatus.OK );
        }

        throw new UnknownIdentifierException( id );
    }

    /**
     * Increment item view count
     *
     * @param id
     * @return
     * @throws UnknownIdentifierException
     */
    @RequestMapping(
            value = "/{id}/view_cnt_inc",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Item> viewCountInc(
            @PathVariable( value = "id" ) String id
    ) throws UnknownIdentifierException {
        Optional<Item> o = itemService.readById( id );

        if( o.isPresent() ){
            //increment view count
            Item item = itemService.incViewCount( o.get() );
            return new ResponseEntity<>(item, HttpStatus.OK);
        }

        throw new UnknownIdentifierException( id );
    }

    /**
     * Create new item and save it
     *
     * @return Code of operation
     */
    @PostMapping(
            value = "/create"
    )
    public ResponseEntity<Object> create(
            @RequestPart( value = "item", required = true ) Item item,
            BindingResult bindingResult,
            @RequestPart( value = "titlePicture", required = false ) MultipartFile titlePicture
    ) throws ItemCreationException {

        if( bindingResult.hasErrors() ){

            List<FieldError> errors = bindingResult.getFieldErrors();

            List<BindingError> validFormErrList = new ArrayList<>(errors.size());

            errors.forEach( e ->
                    validFormErrList.add( new BindingError( e.getField(), e.getDefaultMessage() ) )
            );

            return new ResponseEntity<>( validFormErrList, HttpStatus.BAD_REQUEST );

        } else {

            if( item.getCategory() == null ) throw new ItemCreationException( Item.class.getName() );

            Item createdItem = itemService.update( item );

            if( createdItem == null ) throw new ItemCreationException( Item.class.getName() );

            if( titlePicture != null && !titlePicture.isEmpty() ){
                try {

                    String fileName = generateFileName( item, titlePicture );
                    String uploadedFilename = fileService.uploadFile( titlePicture, fileName );

                    createdItem.setTitlePicture( uploadedFilename == null ? "" : "/picture/" + uploadedFilename);

                    itemService.update( createdItem );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return new ResponseEntity<>( createdItem, HttpStatus.CREATED );
        }
    }

    private String generateFileName(AbstractDocument item, MultipartFile file) {
        //calculate uploading file name
        StringBuilder fileNameSb = new StringBuilder( item.getId() );
        fileNameSb.append("-").append( file.getOriginalFilename() );

        return fileNameSb.toString();
    }

    /**
     * Remove item by id
     *
     * @return id of deleted item
     */
    @RequestMapping(
            value = "/remove/{id}",
            method = RequestMethod.DELETE
    )
    public @ResponseBody String deleteItem(
            @PathVariable( value = "id" ) String id
    ) throws UnknownIdentifierException {

        if( !itemService.remove( id ) ) throw new UnknownIdentifierException( id );

        return id;
    }

    /**
     * Create new item category and save it
     *
     * @return Code of operation and new item category
     */
    @PostMapping(
            value = "/create/category"
    )
    public ResponseEntity<?> createItemCategory(
            @RequestPart( value = "category", required = true) ItemCategory itemCategory,
            BindingResult bindingResult,
            @RequestPart( value = "category_pic", required = false ) MultipartFile categoryPic
    ){
        if( bindingResult.hasErrors() ){

            List<FieldError> errors = bindingResult.getFieldErrors();

            List<BindingError> validFormErrList = new ArrayList<>(errors.size());

            errors.forEach( e ->
                    validFormErrList.add( new BindingError( e.getField(), e.getDefaultMessage() ) )
            );

            return new ResponseEntity<>( validFormErrList, HttpStatus.BAD_REQUEST );

        } else if( itemCategory.getName().equals("") ){
            List<BindingError> validFormErrList = new ArrayList<>(1);
            validFormErrList.add( new BindingError( "name", "Category name is empty"));

            return new ResponseEntity<>( validFormErrList, HttpStatus.BAD_REQUEST );
        } else {
            ItemCategory updatedCategory = null;
            try {
                updatedCategory = itemCategoryService.update( itemCategory );
            } catch( DuplicateKeyException e ){
                e.printStackTrace();
                String msg = new StringBuilder("Category with name ")
                        .append( itemCategory.getName() ).append(" already exist").toString();

                List<BindingError> validFormErrList = new ArrayList<>(1);
                validFormErrList.add( new BindingError( "name", msg));

                return new ResponseEntity<>( validFormErrList, HttpStatus.BAD_REQUEST );
            }

            if ( categoryPic != null && !categoryPic.isEmpty()) {
                try {
                    String fileName = generateFileName( updatedCategory, categoryPic );
                    String uploadedFilename = fileService.uploadFile( categoryPic, fileName );

                    updatedCategory.setTitlePicture(uploadedFilename == null ? "" : "/picture/" + uploadedFilename);

                    itemCategoryService.update( itemCategory );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return new ResponseEntity<>( updatedCategory, HttpStatus.CREATED );
        }
    }

    /**
     * Remove item category by id
     *
     * @return id of deleted item category
     */
    @RequestMapping(
            value = "/category/remove/{id}",
            method = RequestMethod.DELETE
    )
    public @ResponseBody String deleteItemCategory(
            @PathVariable( value = "id" ) String id
    ) throws UnknownIdentifierException {

        if( !itemCategoryService.remove( id ) ) throw new UnknownIdentifierException( id );

        return id;
    }

    /**
     * Rate item
     *
     * @return item with new rating
     */
    @RequestMapping(
            value = "/{id}/rate/{rating}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody ResponseEntity<Item> rate(
            @PathVariable( value = "id") String id,
            @PathVariable( value = "rating") int rating
    ) throws UnknownIdentifierException, IndexOutOfBoundsException {

        Optional<Item> o = itemService.readById(id);
        if(o.isPresent()){
            Item item = o.get();
            Rating r = item.getRating();
            r.rateUp(rating);
            item.setRating(r);

            itemService.update(item);

            return new ResponseEntity<>(item, HttpStatus.OK);
        }

        throw new UnknownIdentifierException(id);
    }

}