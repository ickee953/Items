<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/header?menu_selected_num=0"/>

      <div class="content">
        <div class="container-fluid">
          <button id="add_category_btn" class="btn btn-raised" data-toggle="modal" data-target="#update_category_dialog">
            New category
          </button>
          <!--<button id="toggle_view_btn" class="btn btn-link btn-just-icon btn-round float-right">
            <i class="material-icons">apps</i>
          </button>-->
          <!--
          <button type="submit" class="btn btn-link btn-just-icon btn-round float-right">
            <i class="material-icons">search</i>
          </button>
          <input id="search" placeholder="search..." type="text" class="form-control col-md-2 mt-2 float-right"/>
          -->
          <div class="row">
            <div class="col-md-12">
              <p class="card-category">
                <ul class="nav nav-tabs" data-tabs="tabs">
                <c:forEach var="categoryItem" items="${categoryItems}">
                  <li id="${categoryItem.key.id}" class="nav-item">
                    <a class="btn btn-sm btn-link" href="#tab_${categoryItem.key.id}" data-toggle="tab">
                      <c:out value="${categoryItem.key.name}"/>
                      <div class="ripple-container"></div>
                    </a>
                  </li>
                </c:forEach>
                </ul>
              </p>
              <div id="items_table" class="card">
                <div class="card-body" style="min-height: 300px;">
                  <div class="tab-content">
                    <c:forEach var="categoryItem" items="${categoryItems}">
                    <div class="tab-pane" id="tab_${categoryItem.key.id}">
                      <!-- table view content -->
                      <c:choose>
                      <c:when test="${categoryItem.value.size() == 0}">
                        <div class="row">
                          <div class="col">
                            <button onclick="deleteItemCategory('${categoryItem.key.id}');" class="btn btn-link">
                              <i class="material-icons">delete_outline</i> Delete empty category
                            </button>
                          </div>
                      </c:when>
                      <c:otherwise>
                      <div class="row">
                        <div id="global_check_${categoryItem.key.id}"
                             class="col-md-1 form-check"
                             style="padding: 0px 24px;">
                          <label class="form-check-label">
                            <input class="form-check-input" type="checkbox" value=""
                                   onchange="selectAllItems('${categoryItem.key.id}')"/>
                            <span class="form-check-sign">
                              <span class="check"></span>
                            </span>
                          </label>
                        </div>
                        <div class="col" style="margin-top: 6px;">
                          <button id="global_remove_btn_${categoryItem.key.id}"
                                  onclick="deleteSelectedItems('${categoryItem.key.id}');"
                                  class="btn btn-primary btn-link btn-round btn-just-icon btn-sm"
                                  type="button" disabled>
                            <i class="material-icons">delete_outline</i>
                            <div class="ripple-container"></div>
                          </button>
                          <button id="refresh_btn"
                                  onclick=""
                                  class="btn btn-primary btn-link btn-round btn-just-icon btn-sm"
                                  type="button">
                            <i class="material-icons">refresh</i>
                            <div class="ripple-container"></div>
                          </button>
                          <button id="refresh_btn"
                                  onclick=""
                                  class="btn btn-primary btn-link btn-round btn-just-icon btn-sm"
                                  type="button">
                            <i class="material-icons">more_vert</i>
                            <div class="ripple-container"></div>
                          </button>
                        </div>
                      </c:otherwise>
                      </c:choose>
                        <div class="col">
                          <button onclick='clearUpdateItemDialog();showUpdateItemDialog(${categoryItem.key});'
                                class="btn btn-fill btn-info"
                                style="float: right;"
                                data-toggle="modal"
                                data-target="#update_item_dialog">
                            Add item
                          </button>
                        </div>
                      </div>
                      <table id="items_table_view" class="table">
                        <c:forEach var="item" items="${categoryItem.value}">
                          <tr id="item_${item.id}">
                            <td>
                              <div class="form-check">
                                <label class="form-check-label">
                                  <input class="form-check-input" type="checkbox" value="${item.id}" onchange="itemCheckboxChannged('${categoryItem.key.id}');">
                                  <span class="form-check-sign">
                                    <span class="check"></span>
                                  </span>
                                </label>
                              </div>
                            </td>
                            <td style="text-align: center;">
                            <c:if test='${item.titlePicture == null}'>
                              <i class="material-icons m-3" style="vertical-align: middle;color: #a4acad52;">image_not_supported</i>
                            </c:if>
                            <c:if test="${item.titlePicture != null}">
                              <img id="item_title_picture_${item.id}" style="height: 50px; vertical-align: middle;" class="rounded ml-2 mr-2 mt-2 mb-2" src='${pageContext.request.contextPath}${item.titlePicture}'/>
                            </c:if>
                            </td>
                            <!--<td class="text-primary">
                              ₽<c:out value="${item.price}"/>
                            </td>-->
                            <td style="width: 100%;">
                              <c:out value="${item.descriptionShort}"/>
                            </td>
                            <td class="td-actions text-right">
                              <div style="text-align: right; width: 60px;">
                                <button type="button"
                                        onclick='fillUpdateItemDialog(${item});'
                                        class="btn btn-primary btn-round btn-link btn-sm"
                                        data-toggle="modal"
                                        data-target="#update_item_dialog">
                                  <i class="material-icons">edit</i>
                                </button>
                                <button type="button"
                                        onclick="confirmAndRemoveItem('${item.id}', '${categoryItem.key.id}');"
                                        class="btn btn-danger btn-round btn-link btn-sm">
                                  <i class="material-icons">close</i>
                                </button>
                              </div>
                            </td>
                          </tr>
                        </c:forEach>
                      </table>
                    </div>
                    </c:forEach>
                   </div>
                </div>
              </div>
            </div>
          </div>

        </div>
      </div>

  <div id="update_item_dialog" class="modal fade ml-auto mr-auto">
    <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="card modal-content">
      <div class="card-body mt-5">
        <input id="update_item_id" type="hidden"/>
        <div class="row">
          <div class="col-md-5 up-pic">
            <div id="up_title_pic_wrap" style="position: absolute;">
              <input type="file" id="up_title_pic" style="display:none;"/>
              <label for="up_title_pic">
                <a id="up_title_pic_btn">
                  <i class="material-icons">image_search</i>
                </a>
              </label>
            </div>
            <div class="hover-tools-wrap">
              <img id="item_pic_cropped_img" width="150" class="rounded pointer m-auto" data-toggle="modal" data-target="#crop_dialog">
              <i id="up_item_pic_cropped_cancel" class="material-icons hover-tools pointer" style="display: none;">cancel</i>
            </div>
          </div>
          <div class="col">
            <div class="row">
              <div class="col">
                <div class="form-group">
                  <label class="ibmd-label-floating">Category</label>
                  <input id="update_item_category_name" type="text" class="is-filled form-control dropdown-toggle" data-toggle="dropdown" disabled/>
                  <input id="update_item_category_id" type="hidden"/>
                  <div id="dialog_category_drop_down" class="dropdown-menu">
                    <c:forEach var="categoryItem" items="${categoryItems}">
                    <a href="#" onclick="$('#update_item_category_name').val('${categoryItem.key.name}'), $('#update_item_category_id').val('${categoryItem.key.id}')" class="dropdown-item">
                      <c:out value="${categoryItem.key.name}"/>
                    </a>
                    </c:forEach>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col float-right">
                <div class="form-group">
                  <label class="bmd-label-floating form-check-label">Title</label>
                  <input id="update_item_title" onclick="$(this).prop('placeholder', '');" type="text" class="form-control" required/>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-md-5 float-right">
                <div class="form-group">
                  <label class="bmd-label-floating form-check-label">Price</label>
                  <input id="update_item_price" onclick="$(this).prop('placeholder', '');" type="number" min="0.01" step="0.01" class="form-control" required/>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col float-right">
                <div class="form-group">
                  <label class="bmd-label-floating form-check-label">Description</label>
                  <textarea id="update_item_description_short" type="text" class="form-control" rows="2"></textarea>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <div class="form-group">
              <label>Description Long</label>
              <div class="form-group">
                <label class="bmd-label-floating form-check-label"> Lamborghini Mercy, Your chick she so thirsty, I'm in that two seat Lambo.</label>
                <textarea id="update_item_description" onclick="$(this).prop('placeholder', '');" class="form-control" rows="5" required></textarea>
              </div>
            </div>
          </div>
        </div>
        <div class="text-right">
          <a id="btn_update_item_cancel" href="javascript:;" class="btn btn-fill btn-default" data-dismiss="modal">Cancel</a>
          <a id="btn_update_item" href="javascript:;" class="btn btn-fill btn-info">Update Item</a>
        </div>
        <div class="clearfix"></div>
      </div>
    </div>
    </div>
  </div>

  <div class="modal fade ml-auto mr-auto" id="update_category_dialog">
    <div class="modal-dialog modal-dialog-centered" role="document" style="width: 350px;">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Update category item</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <i class="material-icons">clear</i>
          </button>
        </div>
        <div class="modal-body">
          <input id="up_category_id" type="hidden"/>
          <div class="row">
            <div class="col up-pic">
              <div id="up_category_pic_wrap" style="display: flex; height: 150px;">
                <input type="file" id="up_category_pic" style="display:none;">
                <label for="up_category_pic" style="margin: auto;">
                  <a id="up_category_pic_btn">
                    <i class="material-icons">image_search</i>
                  </a>
                </label>
              </div>
              <div class="hover-tools-wrap">
                <img id="category_pic_cropped_img" width="150" class="rounded pointer m-auto" data-toggle="modal" data-target="#crop_dialog">
                <i id="up_category_pic_cancel" class="material-icons hover-tools pointer" style="display: none;">cancel</i>
              </div>
            </div>
          </div>
          <div class="row mt-4 mb-3">
            <div class="col">
              <div class="form-group">
                <label class="bmd-label-floating form-check-label">Category name</label>
                <input id="update_categoty_name" type="text" class="form-control" required/>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button id="btn_update_category_cancel" type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
            <button id="btn_update_category" type="button" class="btn btn-link">Save</button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div id="crop_dialog" class="modal">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: max-content !important;">
      <div class="modal-content" style="width: min-content !important;">
        <div class="modal-body">
          <div class="row">
            <div class="container jcrop-ux-no-outline">
              <img id="input_crop_image" width="500" src="${pageContext.request.contextPath}/assets/img/crop_no_image.png" class="rounded"/>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button id="close_crop" type="button" class="btn btn-danger btn-link" data-dismiss="modal">Cancel</button>
          <button id="crop_submit" type="button" class="btn btn-link">Save</button>
        </div>
      </div>
    </div>
  </div>

<script>

  $(document).ready(function() {
    <c:if test="${categoryItems.size() > 0}">
      $('#${categoryItems.keySet().toArray()[0].id} a').click();
    </c:if>
  });

  const BASE_URL_BACKEND = 'http://localhost:8080'; //base url

  var items                   = document.getElementById('items');
  var addCategoryBtn           = document.getElementById('add_category_btn');
  var updateItemBtn           = document.getElementById('btn_update_item');
  var updateItemCancelBtn     = document.getElementById("btn_update_item_cancel");
  //var toggleViewBtn           = document.getElementById('toggle_view_btn');
  var updateItemDialog        = document.getElementById("update_item_dialog");
  var upItemPicWrap           = document.getElementById("up_title_pic_wrap");
  var updateItemPic           = document.getElementById("up_title_pic");
  var itemPicCroppedImg       = document.getElementById("item_pic_cropped_img");
  var upItemPicCancel         = document.getElementById("up_item_pic_cropped_cancel");

  var closeCropBtn            = document.getElementById("close_crop");
  var submitCropBtn           = document.getElementById("crop_submit");

  var newCategorySubmitBtn    = document.getElementById('btn_update_category');
  var btnUpdateCategoryCancel = document.getElementById("btn_update_category_cancel");

  var upCategoryPicInput      = document.getElementById("up_category_pic");
  var categoryPicCroppedImg   = document.getElementById("category_pic_cropped_img");
  var inputCropImage          = document.getElementById("input_crop_image");
  var upCategoryPicWrap       = document.getElementById("up_category_pic_wrap");
  var upCategoryPicCancel     = document.getElementById("up_category_pic_cancel");

  var cropDialog              = document.getElementById("crop_dialog");

  const EMPTY_IMG_SRC_URL = null;
  var jcrop, canvas;
  var minSize = Jcrop.Rect.sizeOf(200, 200);
  var maxSize = Jcrop.Rect.sizeOf(2000, 2000);

  updateItemPic.onchange = function(){
    var _URL = window.URL || window.webkitURL;

    if (this.files && this.files[0]) {
      var fileTypes = ['jpg', 'jpeg', 'png'];
      var extension = this.files[0].name.split('.').pop().toLowerCase(),  //file extension from input file
      isSuccess = fileTypes.indexOf(extension) > -1;  //is extension in acceptable types

      if (!isSuccess) {
        closeCropBtn.click();
        alert("Wrong file type!");
        return;
      }

      $(submitCropBtn).removeAttr('disabled');

      img = new Image();
      img.src = _URL.createObjectURL(this.files[0]);

      img.onload = function() {
        result_src = checkSize( img, minSize, maxSize );
        if( result_src != -1 ) {
          //show crop dialog
          inputCropImage.src = result_src;
          itemPicCroppedImg.click();
        } else {
          //hide crop dialog
          closeCropBtn.click();
        }
      }

      img.onerror = function() {
        alert('not a valid file: ' + file.type);
        //hide crop dialog
        closeCropBtn.click();
      }
    }
  }

  upItemPicCancel.onclick = function(){
    if(jcrop) jcrop.destroy();
    canvas = null;

    itemPicCroppedImg.src = '';
    upItemPicWrap.style.display = 'inline-block';
    upItemPicCancel.style.display = 'none';

    updateItemTitlePic = null;
  }

  itemPicCroppedImg.onclick = function(){
    initCropRect( inputCropImage );
  }

  addCategoryBtn.onclick = function(){
    categoryPicCroppedImg.src = '';
    inputCropImage.src = '';
    upCategoryPicWrap.style.display = 'flex';
    upCategoryPicCancel.style.display = 'none';
  }

  upCategoryPicInput.onchange = function(){
    var _URL = window.URL || window.webkitURL;

    if (this.files && this.files[0]) {
      var fileTypes = ['jpg', 'jpeg', 'png'];
      var extension = this.files[0].name.split('.').pop().toLowerCase(),  //file extension from input file
      isSuccess = fileTypes.indexOf(extension) > -1;  //is extension in acceptable types

      if (!isSuccess) {
        closeCropBtn.click();
        alert("Wrong file type!");
        return;
      }

      $(submitCropBtn).removeAttr('disabled');

      img = new Image();
      img.src = _URL.createObjectURL(this.files[0]);

      img.onload = function() {
        result_src = checkSize( img, minSize, maxSize );
        if( result_src != -1 ) {
          //show crop dialog
          inputCropImage.src = result_src;
          categoryPicCroppedImg.click();
        } else {
          //hide crop dialog
          closeCropBtn.click();
        }
      }

      img.onerror = function() {
        alert('not a valid file: ' + file.type);
        //hide crop dialog
        closeCropBtn.click();
      }
    }
  }

  function checkSize( fileResult, min, max ){

    if(fileResult.width < min.w || fileResult.height < min.h){
      alert('Wrong image size. Must be more than ' + min.w + ' x ' + min.h);

      return -1;
    }

    if(fileResult.width > max.w || fileResult.height > max.h){
      alert('Wrong image size. Must be smaller than ' + max.w + ' x ' + max.h);

      return -1;
    }

    return fileResult.src;
  }

  function initCropRect( imgSrc ){

    var rect = Jcrop.Rect.sizeOf(
      imgSrc.naturalWidth,
      imgSrc.naturalHeight
    );

    if(jcrop) jcrop.destroy();

    jcrop = Jcrop.attach( imgSrc, { multi: false });

    widget = jcrop.newWidget(rect.scale( .75, .75).center(imgSrc.naturalWidth, imgSrc.naturalHeight));

    jcrop.listen('crop.change',function(widget,e){
      const pos = widget.pos;
      if( pos.w < minSize.w ) pos.w = minSize.w;
      if( pos.h < minSize.h ) pos.h = minSize.h;
      if( pos.w > maxSize.w ) pos.w = maxSize.w;
      if( pos.h > maxSize.h ) pos.h = maxSize.h;

      widget.render();
    });

    jcrop.focus();
  }

  function cropImage( imgSrc ){
    if(jcrop) {

      let scaleW = parseFloat( imgSrc.naturalWidth / imgSrc.width );
      let scaleH = parseFloat( imgSrc.naturalHeight / imgSrc.height );

      // cropped canvas
      var cropped = document.createElement('canvas');

      cropped.width  = jcrop.active.pos.w * scaleW;
      cropped.height = jcrop.active.pos.h * scaleH;

      var context = cropped.getContext("2d");

      context.drawImage(
          imgSrc,
          jcrop.active.pos.x * imgSrc.naturalWidth / imgSrc.width,
          jcrop.active.pos.y * imgSrc.naturalHeight / imgSrc.height,
          jcrop.active.pos.w * imgSrc.naturalWidth / imgSrc.width,
          jcrop.active.pos.h * imgSrc.naturalHeight / imgSrc.height,
          0,
          0,
          jcrop.active.pos.w * imgSrc.naturalWidth / imgSrc.width,
          jcrop.active.pos.h * imgSrc.naturalHeight / imgSrc.height
      );
      // img, sx, sy, swidth, sheight, dx, dy, dwidth, dheight
      // s = source
      // d = destination

      return cropped;
    }

    return -1;
  }

  function hideCropDialog(){
    cropDialog.style.display = "none";
    //reset file input
    upCategoryPicInput.value = "";
    upCategoryPicInput.files = null;
  }

  categoryPicCroppedImg.onclick = function(){
    initCropRect( inputCropImage );
  }

  upCategoryPicCancel.onclick = function(){
    if(jcrop) jcrop.destroy();
    canvas = null;

    categoryPicCroppedImg.src = '';
    upCategoryPicWrap.style.display = 'flex';
    upCategoryPicCancel.style.display = 'none';

    //updateCategoryItemPic = null;
  }

  closeCropBtn.onclick = function(){
    hideCropDialog();
  };

  submitCropBtn.onclick = function(){
    var image = new Image();
    image.crossOrigin = "Anonymous";
    image.src = inputCropImage.src;
    image.width = inputCropImage.width;
    image.height = inputCropImage.height;
    image.naturalWidth = inputCropImage.naturalWidth;
    image.naturalHeight = inputCropImage.naturalHeight;

    image.onload = function(){
      if(jcrop) {
        canvas = cropImage(this);
        if( canvas != -1 ){
          categoryPicCroppedImg.src = canvas.toDataURL();
          upCategoryPicWrap.style.display = 'none';
          upCategoryPicCancel.style.display = 'block';

          itemPicCroppedImg.src = canvas.toDataURL();
          upItemPicWrap.style.display = 'none';
          upItemPicCancel.style.display = 'block';

          if(jcrop) jcrop.destroy();
          //hide crop dialog
          closeCropBtn.click();
        }
      }
    }
  };

  newCategorySubmitBtn.onclick = function(){
    var data = {}

    if( $('#up_category_id').val() == '' ) {
      data["id"] = null;
    } else {
      data["id"] = $('#up_category_id').val();
    }
    data["name"]          = $('#update_categoty_name').val();
    //data["titlePicture"]  = ;

    var button = $(this);

    button.prop("disabled", true);

    let formData = new FormData();

    formData.append("category", new Blob([JSON.stringify(data)], {
      type: 'application/json'
    }));

    if( canvas != null ) {
      let file = null;
      let blob = canvas.toBlob(function(blob) {
            //todo generate file name ( if create new category then id = null )
            //fileName = "category_pic_" + data["id"] + ".png";
            fileName = "category_pic.png";
            //fileName = upCategoryPicInput.files[0].name;
            file = new File([blob], fileName, { type: 'image/png' });
            formData.append("category_pic", file);

            updateCategoryRequest( formData, button );
      }, 'image/png');
    } else {
      updateCategoryRequest( formData, button );
    }
  }

  function updateCategoryRequest( formData, updateBtn ){
    $.ajax({
            url: '${pageContext.request.contextPath}/items/create/category',
            type: 'POST',
            headers: { "${_csrf.headerName}" : "${_csrf.token}" },
            data: formData,
            contentType: 'multipart/form-data',
            processData : false, // prevent jQuery from automatically
            contentType : false, // transforming the data into a query string
            success: function (data) {
                console.log("Success:\n" + JSON.stringify(data));
                updateBtn.prop("disabled", false);

                //refresh ui
                $('#items_table .nav-link').removeClass('active');
                $('#items_table .tab-pane').removeClass('active');

                var li = $("<li/>", {
                  "id": data.id,
                  "class": 'nav-item'
                }).appendTo( $("#items_table").parent().find($(".nav-tabs")) );

                var a = $("<a/>", {
                  "class": 'btn btn-sm btn-link',
                  "href": '#tab_' + data.id,
                  "data-toggle": 'tab',
                  "text": data.name
                }).appendTo( li );

                $("<div/>", {
                  "class": 'ripple-container'
                }).appendTo( a );

                //append table header for created category

                var tab_pane = $("<div/>", {
                  "class": 'tab-pane active',
                  "id": 'tab_' + data.id
                }).appendTo( $('#items_table .tab-content') );

                var table = $("<table/>", {
                  "class": 'table'
                }).appendTo( tab_pane );

                //var thead = $("<thead/>", {}).appendTo( table );

                //var tr = $("<tr/>", {}).appendTo( thead )
                var tr = $("<tr/>", {}).appendTo( table )

                tr.append('<th><button onclick="deleteItemCategory(\'' + data.id + '\');" class="btn btn-link"><i class="material-icons">delete_outline</i> Delete empty category</button></th>');

                tr.append("<th></th><th></th><th></th><th class='text-right'><button onclick='clearUpdateItemDialog();showUpdateItemDialog("+JSON.stringify(data)+");' class='btn btn-fill btn-info'>Add item</button></th>");

                //$('<tbody/>',{}).appendTo( table );

                //hide add category pannel

                $('#btn_add_categoty').show();
                $('#btn_close_add_category').hide();
                $('#new_category_name').hide();
                $('#btn_save_new_category').hide();

                //hide modal dialog
                btnUpdateCategoryCancel.click();

                //clear text input
                $('#update_categoty_name').val('');
            },
            error: function (err) {
                updateBtn.prop("disabled", false);

                if( err.status == 400 ){
                    //show error message
                    for( i = 0; i < err.responseJSON.length; i++ ){
                      $("#update_categoty_"+err.responseJSON[i].field).val("");
                      $("#update_categoty_"+err.responseJSON[i].field).prop('placeholder', err.responseJSON[i].message);
                      $("#update_categoty_"+err.responseJSON[i].field).parent($('.form-group')).addClass('is-focused');

                      console.log("@"+err.responseJSON[i].field+":"+err.responseJSON[i].message+"\n");
                    }
                } else {
                    console.log(JSON.stringify(err));
                }
            }
    });
  }

  var updateItemTitlePic;
  updateItemBtn.onclick = function() {

    $('#btn_update_item').prop("disabled", true);

    var item = {}
    var category = {}

    category["id"] = $('#update_item_category_id').val();
    category["name"] = $('#update_item_category_name').val();

    if( $('#update_item_id').val() != '' ) item["id"] = $('#update_item_id').val();

    item["title"] = $('#update_item_title').val();
    item["price"] = $('#update_item_price').val();
    item["descriptionShort"] = $('#update_item_description_short').val();
    item["description"] = $('#update_item_description').val();
    item["category"] = category;
    item["titlePicture"] = updateItemTitlePic;

    let formData = new FormData();

    formData.append("item", new Blob([JSON.stringify(item)], {
        type: 'application/json'
    }));

    if( canvas != null ) {
      let file = null;
      let blob = canvas.toBlob(function(blob) {
        //fileName = updateItemPic.files[0].name;
        fileName = "item_pic.png";
        file = new File([blob], fileName, { type: 'image/png' });
        formData.append("titlePicture", file);
        updateItemRequest( formData );
      }, 'image/png');
    } else {
      updateItemRequest( formData );
    }
  }

  function updateItemRequest( formData ) {
    $.ajax({
        url: '${pageContext.request.contextPath}/items/create',
        type: 'POST',
        data: formData,
        headers: { "${_csrf.headerName}" : "${_csrf.token}" },
        contentType: 'multipart/form-data',
        processData : false, // prevent jQuery from automatically
        contentType : false, // transforming the data into a query string
        success: function (data) {
            console.log("Success:\n" + JSON.stringify(data));

            $("#btn_update_item").prop("disabled", false);

            is_new = true;

            $('#item_'+data.id+' td').each( function(i, obj){
              is_new = false;
              switch( i ){
                case 1:
                  $(obj).html('');
                  //add title image
                  if( data.titlePicture == null ){
                    $('<i>', {
                      "class": "material-icons",
                      "text": "image_not_supported"
                    }).appendTo($(obj));
                  } else {
                    $('<img>', {
                      "id": "item_title_picture_"+data.id,
                      "style": "height: 50px;",
                      "class": "rounded ml-2 mr-2 mt-2 mb-2",
                      "src": '${pageContext.request.contextPath}'+data.titlePicture
                    }).appendTo($(obj));
                  }

                  break;
                case 2:
                  $(obj).html(data.descriptionShort);

                  break;

                case 3: {
                  $(obj).html('');

                  div2 = $('<div/>', {
                    "style": "text-align: right; width: 100%;"
                  }).appendTo( $(obj) );

                  $('<i/>', {
                    "class": "material-icons",
                    "text": "edit"
                  }).appendTo(
                    $('<button/>', {
                      "type": "button",
                      "class": "btn btn-primary btn-link btn-sm",
                      "onclick": 'fillUpdateItemDialog('+JSON.stringify(data)+');',
                      "data-toggle": "modal",
                      "data-target": "#update_item_dialog"
                    }).appendTo( div2 )
                  );

                  $('<i/>', {
                    "class": "material-icons",
                    "text": "close"
                  }).appendTo(
                    $('<button/>', {
                      "type": "button",
                      "class": "btn btn-danger btn-link btn-sm",
                      "onclick": "confirmAndRemoveItem('"+data.id+"', '"+data.category.id+"');"
                    }).appendTo( div2 )
                  );
                  break;
                }
              }
            });

            if( is_new ){
              tr = $('<tr/>', {
                "id": "item_"+data.id
              }).appendTo($("#tab_"+data.category.id+" table"));

              td = $('<td/>', {}).appendTo( tr );

              div = $('<div/>', {
                "class": "form-check"
              }).appendTo( td );

              label = $('<label/>', {
                "class": "form-check-label"
              }).appendTo( div );

              checkbox = $('<input/>', {
                "class": "form-check-input",
                "type": "checkbox",
                "value": data.id,
                "onchange": "itemCheckboxChannged('"+data.category.id+"');"
              }).appendTo( label );

              span1 = $('<span/>', {
                "class": "form-check-sign"
              }).appendTo( label );

              span2 = $('<span/>', {
                "class": "check"
              }).appendTo( span1 );

              //add title image
              imgTd = $('<td>', {
                "style": "text-align: center;"
              }).appendTo( tr )

              if( data.titlePicture == null ){
                $('<i>', {
                  "class": "material-icons",
                  "text": "image_not_supported"
                }).appendTo(imgTd);
              } else {
                $('<img>', {
                  "id": "item_title_picture_"+data.id,
                  "style": "height: 50px;",
                  "class": "rounded ml-2 mr-2 mt-2 mb-2",
                  "src": '${pageContext.request.contextPath}'+data.titlePicture
                }).appendTo(imgTd);
              }

              $('<td/>', {
                "text": data.descriptionShort,
                "style": "width: 100%;"
              }).appendTo( tr );

              td2 = $('<td/>', {
                "class": "td-actions text-right"
              }).appendTo(tr);

              div2 = $('<div/>', {
                "style": "text-align: right; width: 100%;"
              }).appendTo( td2 );

              $('<i/>', {
                "class": "material-icons",
                "text": "edit"
              }).appendTo(
                $('<button/>', {
                  "type": "button",
                  "class": "btn btn-primary btn-link btn-sm",
                  "onclick": 'fillUpdateItemDialog('+JSON.stringify(data)+'); showUpdateItemDialog('+JSON.stringify(data.category)+');'
                }).appendTo( div2 )
              );

              $('<i/>', {
                "class": "material-icons",
                "text": "close"
              }).appendTo(
                $('<button/>', {
                  "type": "button",
                  "class": "btn btn-danger btn-link btn-sm",
                  "onclick": "confirmAndRemoveItem('"+data.id+"', '"+data.category.id+"');"
                }).appendTo( div2 )
              );
            }

            updateItemCancelBtn.click();

            updateItemTableHead( data.category.id );
        },
        error: function (errors) {
            $("#btn_update_item").prop("disabled", false);
            //fill error form inputs

            //show error message
            for( i = 0; i < errors.responseJSON.length; i++ ){
              $("#update_item_"+errors.responseJSON[i].field).prop('placeholder', errors.responseJSON[i].message);
              $("#update_item_"+errors.responseJSON[i].field).parent($('.form-group')).addClass('is-focused');

              console.log("@"+errors.responseJSON[i].field+":"+errors.responseJSON[i].message+"\n");
            }
        }
    });
  }

    /*toggleViewBtn.onclick = function() {
        if( currentView == 0 ) {
            currentView = 1;
            $(this).find('.material-icons').html('menu');
        } else {
            currentView = 0;
            $(this).find('.material-icons').html('apps');
        }
    }*/

    function deleteItemCategory( category_id ){
        Swal.fire({
          text: "Are you sure about deleting this category item?",
          animation: false,
          showCancelButton: true,
          confirmButtonText: "Delete It",
          reverseButtons: true,
          focusConfirm: false,
          focusCancel: true,
          confirmButtonClass: "btn btn-fill btn-danger",
          cancelButtonClass: "btn btn-fill btn-default",
            buttonsStyling: false
          }).then(function(result) {
            if (result.value) {
              $.ajax({
                url: '${pageContext.request.contextPath}/items/category/remove/'+category_id,
                type: 'DELETE',
                headers: { "${_csrf.headerName}" : "${_csrf.token}" },
                success: function (data) {
                  console.log(data);

                  $('#' + category_id).remove();
                  $('#tab_' + category_id).remove();
                },
                error: function (e) {
                  console.log('Error in Operation');
                }
              });
            }
        });
    }

    function updateItemTableHead( category_id ){
      $('#tab_'+category_id+' table th').each(function(i, obj){
        if( $('#tab_'+category_id+' table tr').length == 0 ){
          switch(i){
            case 0: {
              $(obj).html('');

              $('<button/>', {
                  "class": "btn btn-link",
                  "onclick": "deleteItemCategory('"+category_id+"');"
              }).appendTo( $(obj) ).append('<i class="material-icons">delete_outline</i> Delete empty category');

              break;
            }
            case 1: {
              $(obj).html('');

              break;
            }

            case 2: {
              $(obj).html('');

              break;
            }
          }
        } else {
          switch(i){
            case 0: {
              $(obj).html('');

              label = $('<label/>', {
                  "class": "form-check-label"
              }).appendTo(
                  $('<div/>', {
                    "id": "global_check_"+category_id,
                    "class": "form-check"
                }).appendTo( $(obj) )
              );

              $('<input/>', {
                  "class": "form-check-input",
                  "type": "checkbox",
                  "value": "",
                  "onchange": "selectAllItems('"+category_id+"')"
              }).appendTo( label );

              $('<span/>', {
                  "class": "check"
              }).appendTo(
                  $('<span/>', {
                    "class": "form-check-sign"
                }).appendTo( label )
              );

              break;
            }
            case 1: {
              $(obj).html('');

              $('<button/>', {
                  "id": "global_remove_btn_"+category_id,
                  "onclick": "deleteSelectedItems('"+category_id+"');",
                  "class": "btn btn-fill btn-danger",
                  "disabled": true
              }).appendTo( $(obj) ).append('<i class="material-icons">delete_outline</i> Remove selected');

              break;
            }
            case 2: {
              $(obj).html('Price');

              break;
            }
          }
        }
      });
    }

    function clearUpdateItemDialog(){
        //clear category fields
        $('#update_item_category_id').val('');
        $('#update_item_category_name').val('');

        //clear item fields
        $('#update_item_id').val('');
        $('#update_item_title').val('');
        $('#update_item_price').val('');
        $('#update_item_description_short').val('');
        $('#update_item_description').val('');

        $('#update_item_title').parent('div').removeClass('is-filled');
        $('#update_item_price').parent('div').removeClass('is-filled');
        $('#update_item_description_short').parent('div').removeClass('is-filled');
        $('#update_item_description').parent('div').removeClass('is-filled');

        updateItemPic.value = "";
        updateItemPic.files = null;
        itemPicCroppedImg.src = '';

        upItemPicWrap.style.display = 'block';
        upItemPicCancel.style.display = 'none';

        updateItemTitlePic = null;
        inputCropImage.src = '';
    }

    function fillUpdateItemDialog(item){
        var category = item.category;

        $('#update_item_category_id').val(category["id"]);
        $('#update_item_category_name').val(category["name"]);

        $('#update_item_id').val(item["id"]);
        $('#update_item_title').val(item["title"]);

        if( item["titlePicture"] != null ){
            itemPicCroppedImg.src = '${pageContext.request.contextPath}' + item["titlePicture"];
            inputCropImage.src = '${pageContext.request.contextPath}'+item["titlePicture"];
            updateItemTitlePic = '${pageContext.request.contextPath}'+item["titlePicture"];
            upItemPicCancel.style.display = 'block';
            upItemPicWrap.style.display = 'none';
        } else {
            itemPicCroppedImg.src = '';
            inputCropImage.src = '';
            updateItemTitlePic = null;
            upItemPicCancel.style.display = 'none';
            upItemPicWrap.style.display = 'inline-block';
        }

        $('#update_item_price').val(item["price"]);
        $('#update_item_description_short').val(item["descriptionShort"]);
        $('#update_item_description').val(item["description"]);

        $('#update_item_title').parent('div').addClass('is-filled');
        $('#update_item_price').parent('div').addClass('is-filled');
        $('#update_item_description_short').parent('div').addClass('is-filled');
        $('#update_item_description').parent('div').addClass('is-filled');
    }

    function confirmAndRemoveItem( item_id, category_id ){
        Swal.fire({
          text: "Are you sure about deleting this item?",
          animation: false,
          showCancelButton: true,
          confirmButtonText: "Delete It",
          reverseButtons: true,
          focusConfirm: false,
          focusCancel: true,
          confirmButtonClass: "btn btn-fill btn-danger",
          cancelButtonClass: "btn btn-fill btn-default",
          buttonsStyling: false
        }).then(function(result) {
          if (result.value) {
            removeItem( item_id, category_id );
          }
        });
    }

    function removeItem( item_id, category_id ){
        $.ajax({
          url: '${pageContext.request.contextPath}/items/remove/'+item_id,
          type: 'DELETE',
          headers: { "${_csrf.headerName}" : "${_csrf.token}" },
          success: function (data) {
            console.log('Item with id: '+data+' deleted successful.');

            $("#item_"+item_id).remove();

            updateItemTableHead( category_id );
          },
          error: function (e) {
            console.log('Error in Operation: deleteItem with id: '+item_id+'\n\n');
            console.log(e)
          }
        });
    }

    function deleteSelectedItems( category_id ){

        Swal.fire({
          text: "Are you sure about deleting selected items?",
          animation: false,
          showCancelButton: true,
          confirmButtonText: "Delete",
          reverseButtons: true,
          focusConfirm: false,
          focusCancel: true,
          confirmButtonClass: "btn btn-fill btn-danger",
          cancelButtonClass: "btn btn-fill btn-default",
          buttonsStyling: false
        }).then(function(result) {
          if (result.value) {
            $("#tab_"+category_id+" .form-check-input").each(function(i, obj){
              if($(this).prop('checked')) {
                removeItem($(this).val(), category_id);
              }
            });
          }
        });

    }

    function isAnyItemSelected( category_id ){
        var isAnySelected = false

        $("#tab_"+category_id+" .form-check-input").each(function(i, obj){
          if($(this).prop('checked')) isAnySelected = true;
        });

        return isAnySelected;
    }

    function itemCheckboxChannged( category_id ){
        if( isAnyItemSelected( category_id ) == true ){
          $("#global_remove_btn_"+category_id).prop('disabled', false);
        } else {
          $("#global_remove_btn_"+category_id).prop('disabled', true);
          $("#global_check_"+category_id+" .form-check-input").prop('checked', false);
        }
    }

    function selectAllItems( category_id ){
        $("#tab_"+category_id+" .form-check-input").each(function(i, obj){
          if( $("#global_check_"+category_id+" .form-check-input").prop('checked') ){
            $(this).prop('checked', true);
          } else {
            $(this).prop('checked', false);
          }
          itemCheckboxChannged( category_id );
        });
    }

    function showUpdateItemDialog( category ){
        //show modal dialog
        $('#update_item_category_name').val( category.name );
        $('#update_item_category_id').val( category.id );

        updateItemDialog.style.display = "block";
    }

    function hideNewItemDialog(){
        updateItemDialog.style.display = "none";
        //clear fields
        clearUpdateItemDialog();
    }
</script>

<jsp:include page="/footer"/>