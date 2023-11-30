<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/header?menu_selected_num=1"/>

      <div class="content">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-1">
              <h4>Пользователи</h4>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12">
              <div id="items_table" class="card">
                <div class="card-body" style="min-height: 300px;">
                  <div class="tab-content">
                    <div class="row">
                      <div id="global_check_users"
                           class="col-md-1 form-check"
                           style="padding: 0px 24px;">
                        <label class="form-check-label">
                          <input class="form-check-input" type="checkbox" value=""
                                 onchange="selectAllItems()"/>
                          <span class="form-check-sign">
                            <span class="check"></span>
                          </span>
                        </label>
                      </div>
                      <div class="col" style="margin-top: 6px;">
                        <button id="global_remove_btn"
                                onclick="deleteSelectedItems();"
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
                        <div class="col">
                          <button id="add_user_btn" onclick="clearUpdateItemDialog();"
                                class="btn btn-fill btn-info"
                                style="float: right;"
                                data-toggle="modal" data-target="#update_item_dialog">
                            Добавить
                          </button>
                        </div>
                    </div>
                    <table id="items_table_view" class="table">
                      <c:forEach var="item" items="${users}">
                      <tr id="item_${item.id}">
                        <td>
                          <div class="form-check">
                            <label class="form-check-label">
                              <input class="form-check-input" type="checkbox" value="${item.id}" onchange="">
                              <span class="form-check-sign">
                                <span class="check"></span>
                              </span>
                            </label>
                          </div>
                        </td>
                        <td style="text-align: center;width: 50px; height: 50px;">
                          <c:if test='${item.avatar == null}'>
                            <i class="material-icons" style="vertical-align: middle;color: #a4acad52;">account_circle</i>
                          </c:if>
                          <c:if test='${item.avatar != null}'>
                            <img id="item_title_picture_${item.id}" style="width: 50px; height: 50px;"
                                 class="rounded-circle ml-2 mr-2 mt-2 mb-2" src='${item.avatar}'/>
                          </c:if>
                        </td>
                        <td style="width: 100%;">
                          ${item.email}
                        </td>
                        <td class="td-actions text-right" style="float: right; width: 100px;">
                          <div>
                            <button type="button" rel="tooltip"
                                    title="Edit Item" class="btn btn-primary btn-round btn-link btn-sm"
                                    onclick='clearUpdateItemDialog();fillUpdateItemDialog(${item});'
                                    data-toggle="modal" data-target="#update_item_dialog">
                              <i class="material-icons">edit</i>
                            </button>
                            <button type="button"
                                    onclick="confirmAndRemoveItem('${item.id}');"
                                    rel="tooltip" title="Remove"
                                    class="btn btn-danger btn-round btn-link btn-sm">
                              <i class="material-icons">close</i>
                            </button>
                          </div>
                        </td>
                      </tr>
                      </c:forEach>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="modal fade ml-auto mr-auto" id="update_item_dialog">
        <div class="modal-dialog modal-dialog-centered" role="document" style="width: 350px;">
          <div class="modal-content" style="width: 100%;">
            <div class="modal-header">
            </div>
            <div class="modal-body">
              <form>
                <input id="update_item_id" type="hidden"/>
                <div class="row">
                  <div class="col">
                    <div class="row">
                      <div class="col">
                        <div class="form-group">
                          <label class="bmd-label-floating form-check-label">Логин</label>
                          <input id="update_email" onclick="$(this).prop('placeholder', '');" type="text" class="form-control" required/>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col">
                        <div class="form-group">
                          <label class="bmd-label-floating form-check-label">Пароль</label>
                          <input id="update_password" onclick="$(this).prop('placeholder', '');" type="password" class="form-control" required/>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col">
                        <div class="form-group">
                          <label class="bmd-label-floating form-check-label">Подтверждение пароля</label>
                          <input id="update_password_confirm" onclick="$(this).prop('placeholder', '');" type="password" class="form-control" required/>
                        </div>
                      </div>
                    </div>
                    <div class="row mt-2">
                      <div class="col">
                        <div id="update_user_auth" class="form-group">
                          <label class="">Права доступа:</label>
                          <span style="width: 65%;" class="float-right badge badge-pill badge-primary p-2 dropdown-toggle pointer"
                                data-toggle="dropdown">
                                добавить
                          </span>
                          <br>
                          <div style="min-height: 55px;"></div>
                          <br>
                          <input id="update_role_id" type="hidden" value="">
                            <div id="dialog_role_drop_down" class="dropdown-menu">
                            <c:forEach var="role" items="${roles}" varStatus="index">
                              <a href="#" onclick='addUserRole(${role});' class="dropdown-item">
                                <c:out value="${role.name}"/>
                              </a>
                            </c:forEach>
                            </div>
                          </input>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-link" data-dismiss="modal">Отменить</button>
              <button id="btn_update_item" type="button" class="btn btn-link">Сохранить</button>
            </div>
          </div>
        </div>
      </div>

<script>

  var updateItemDialog     = document.getElementById("update_item_dialog");
  var updateItemBtn        = document.getElementById("btn_update_item");

  var inputUpdateId               = document.getElementById("update_item_id");
  var inputUpdateemail            = document.getElementById("update_email");
  var inputUpdatePassword         = document.getElementById("update_password");
  var inputUpdatePasswordConfirm  = document.getElementById("update_password_confirm");

  var updateUserRoles = []
  function clearUpdateItemDialog(){
    $(inputUpdateId).val('');

    //clear item fields
    $(inputUpdateemail).val('');
    $(inputUpdatePassword).val('');
    $(inputUpdatePasswordConfirm).val('');

    $(inputUpdateemail).parent('div').removeClass('is-filled');

    //clear authorities
    $('#update_user_auth div')[0].innerHTML = '';
    updateUserRoles = [];
  }

  function fillUpdateItemDialog(item){
    $(inputUpdateId).val(item["id"]);
    $(inputUpdateemail).val(item["email"]);

    $(inputUpdateemail).parent('div').addClass('is-filled');

    //fill user authorities
    for( var i = 0; i < item["roles"].length; i++ ){
      addUserRole( item["roles"][i] );
    }
  }

  updateItemBtn.onclick = function(){
    let item         = {};

    if( $(inputUpdateId).val() == '' ){
      item['id'] = null;
    } else {
      item['id'] = $(inputUpdateId).val();
    }
    item['email']  = $(inputUpdateemail).val();
    item['roles']  = updateUserRoles;

    //check for email not empty
    if( item['email'] == '' ){
      alert("Поле логин обязательно для заполнения");

      return;
    }

    if( item['id'] == null ){
      //check password confirmation
      if( $(inputUpdatePassword).val() == '' ){
        alert("Пароль для нового пользователя не может буть пустым");

        return;
      }
    }

    if( $(inputUpdatePassword) != '' && $(inputUpdatePassword).val() != $(inputUpdatePasswordConfirm).val() ){
      alert("Пароли не совпадают");

      return;
    }

    item['password']  = $(inputUpdatePassword).val();

    let button = $(this);
    button.prop("disabled", true);

    $.ajax({
      url: '${pageContext.request.contextPath}/users/update',
      type: 'POST',
      headers: { "${_csrf.headerName}" : "${_csrf.token}" },
      data: JSON.stringify(item),
      contentType: 'application/json',
      processData : false, // prevent jQuery from automatically
      success: function (data) {
        button.prop("disabled", false);

        console.log("Success:\n" + JSON.stringify(data));

        //refresh page
        location.reload();
      },
      error: function (errors) {
        console.log(errors.statusText+"\n");
        button.prop("disabled", false);
      }
    });
  }

  function addUserRole( data ){
    for( i = 0; i < updateUserRoles.length; i++ ) if( updateUserRoles[i]["id"] == data["id"] ) return -1;

    updateUserRoles.push( data );

    wrap = $('<div/>', {
      'class': 'hover-tools-wrap mt-3 mb-2 mr-2'
    }).appendTo(
      $('#update_user_auth div')[0]
    );
    span = $('<span/>', {
      'class': 'badge badge-pill badge-secondary p-2',
      'text': data['name']
    }).appendTo( wrap );
      i = $('<i/>', {
        'id': 'role_cancel_' + data['id'],
        'class': 'material-icons hover-tools pointer',
        'text': 'cancel',
        'onclick': 'removeUserRole(' + JSON.stringify(data) + ');'
    }).appendTo( wrap );
  }

  function removeUserRole( data ){
    updateUserRoles.pop( data );
    $('#role_cancel_' + data["id"]).parent().remove();
  }

  function addUserBrand( data ){
    var content = $('<div/>', {
      'class': 'ml-2 mt-2 text-center'
    }).appendTo( $("#update_user_brands") );
    var img = $('<img/>', {
      'class': 'rounded-circle',
      'width': '70px',
      'height': '70px',
      'src': data["titlePicture"]
    }).appendTo( content );

    /*var title = $('<h5/>', {
      'text': data["title"]
    }).appendTo( content );*/
  }

  function confirmAndRemoveItem( item_id ){
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
        removeItem( item_id );
      }
    });
  }

  function removeItem( item_id ){
    $.ajax({
      url: '${pageContext.request.contextPath}/users/remove/'+item_id,
      type: 'DELETE',
      headers: { "${_csrf.headerName}" : "${_csrf.token}" },
      success: function (data) {
        console.log('Item with id: '+data+' deleted successful.');

        $("#item_"+item_id).remove();
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
        $("#items_table_view .form-check-input").each(function(i, obj){
          if($(this).prop('checked')) {
            removeItem($(this).val(), category_id);
          }
        });
      }
    });
  }

  function isAnyItemSelected(){
    var isAnySelected = false

    $("#items_table_view .form-check-input").each(function(i, obj){
      if($(this).prop('checked')) isAnySelected = true;
    });

    return isAnySelected;
  }

  //header checkbox
  function itemCheckboxChannged(){
    if( isAnyItemSelected() == true ){
      $("#global_remove_btn").prop('disabled', false);
    } else {
      $("#global_remove_btn").prop('disabled', true);
      $("#global_check_users .form-check-input").prop('checked', false);
    }
  }

  function selectAllItems(){
    $("#items_table_view .form-check-input").each(function(i, obj){
      if( $("#global_check_users .form-check-input").prop('checked') ){
        $(this).prop('checked', true);
      } else {
        $(this).prop('checked', false);
      }
      itemCheckboxChannged();
    });
  }

</script>

<jsp:include page="/footer"/>