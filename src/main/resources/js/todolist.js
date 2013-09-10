var section = 1;

//--> this function change todo state
function ru_mail_changeToDoItem(event, cfId) {
    var target = jQuery(event.target);
    var sharesObj = jQuery.evalJSON(jQuery("#" + cfId).val());
    var targetToDo = jQuery(target).parent().next().find("span").text();

    for (var objId in sharesObj) {
        if (sharesObj[objId]["id"] == targetToDo) {
            if (jQuery(target).attr('checked')) {
                sharesObj[objId]["type"] = 'done';
                jQuery(target).parent().next().children().removeClass("todo_undone").addClass("todo_done");
            } else {
                sharesObj[objId]["type"] = 'todo';
                jQuery(target).parent().next().children().removeClass("todo_done").addClass("todo_undone");
            }
            jQuery("#" + cfId).val(jQuery.toJSON(sharesObj));
        }
    }
}

//--> this function remove todo
function ru_mail_removeToDoItem(event, cfId) {
    event.preventDefault();

    var target = jQuery(event.target);
    var sharesObj = jQuery.evalJSON(jQuery("#" + cfId).val());
    var targetToDo = jQuery(target).parent().prev().prev().find("span").html();

    var num = -1;
    for (var objId in sharesObj) {
        if (sharesObj[objId]["id"] == targetToDo) {
            num = objId;
            break;
        }
    }

    if (num != -1) {
        sharesObj.splice(num, 1);
    }

    jQuery("#" + cfId).val(jQuery.toJSON(sharesObj));
    jQuery(event.target).parent().parent().remove();
}

//--> this function edit todo
function ru_mail_editToDoItem(event, cfId) {
    event.preventDefault();

    var target = jQuery(event.target);
    var sharesObj = jQuery.evalJSON(jQuery("#" + cfId).val());

    var targetToDo1 = jQuery(target).parent().prev().find("textarea");
    if (targetToDo1.html()) {
        return;
    }

    var targetToDo = jQuery(target).parent().prev().find("span");

    section++;
    var textarea = '<div><textarea id=\"ddd' + section + '\">' + jQuery(targetToDo).html()+'</textarea>';
    var saveBtn = '<input type="button" value=\"' + AJS.I18n.getText("ru.mail.todolist.savetodo") + '\" id="savebtn' + section + '" class="saveButton" />';
    var cancelBtn = '<input type="button" value=\"' + AJS.I18n.getText("ru.mail.todolist.canceltodo") + '\" id="cancelbtn' + section + '" class="cancelButton" />';
    var button = '<div>' + saveBtn + cancelBtn + '</div></div>';

    jQuery(targetToDo).after(textarea + button).hide();
    jQuery('#savebtn' + section).click(function() {
        var textar = jQuery(this).parent().parent().find("textarea");
        var newvalue = textar.val();
        if (!newvalue) return;

        var targetToDoVal = jQuery(targetToDo).text();
        var sharesObj = jQuery.evalJSON(jQuery("#" + cfId).val());
        for (var objId in sharesObj) {
            if (sharesObj[objId]["id"] == targetToDoVal) {
                sharesObj[objId]["id"] = newvalue;
                jQuery("#" + cfId).val(jQuery.toJSON(sharesObj));
            }
        }

        jQuery(textar).parent().remove();
        jQuery(targetToDo).html(newvalue).show();
    });
    jQuery('#cancelbtn' + section).click(function() {
        jQuery(this).parent().parent().remove();
        jQuery(targetToDo).show();
    });
}

//--> this function add todo
function ru_mail_addToDoItem(event, cfId, currtime) {
    var todoinput = jQuery("#todoinput").val();

    if (todoinput) {
        // check existing
        var sharesObj = jQuery.evalJSON(jQuery("#" + cfId).val());
        for (var objId in sharesObj) {
            if (sharesObj[objId]["id"] == todoinput) {
                jQuery("#todoaddblk" + currtime).animate({backgroundColor: "red"}, 500, function() { jQuery("#todoaddblk" + currtime).animate({backgroundColor: "white"}, 500);});
                return;
            }
        }

        //--> add row
        var tr = "<tr class=\"initial\" onMouseOver=\"this.className='highlight'\" onMouseOut=\"this.className='normal'\">";
        var td1 = "<td width=\"16px\" class=\"checkboxcolumn\"><input onchange=\"ru_mail_changeToDoItem(event, '" + cfId + "');\" class=\"todocheckbox\" type=\"checkbox\" value=\"true\"/></td>";
        var td2 = "<td class=\"wrapcolumn\"><span class=\"todo_undone\">" + todoinput + "</span></td>";
        var td3 = "<td width=\"32px\"><a href=\"#\" class=\"todoeditbtn\" onclick=\"ru_mail_editToDoItem(event, '" + cfId + "');\"/></td>";
        var td4 = "<td width=\"32px\"><a href=\"#\" class=\"todoremovebtn\" onclick=\"ru_mail_removeToDoItem(event, '" + cfId + "');\"/></td>";
        jQuery("#" + currtime).append(tr + td1 + td2 + td3 + td4 + "</tr>");

        // clear input
        jQuery("#todoinput").val("");

        var itemObj = new Object();
        itemObj["id"] = todoinput;
        itemObj["type"] = "todo";
        sharesObj.push(itemObj);
        jQuery("#" + cfId).val(jQuery.toJSON(sharesObj));
    }
}
