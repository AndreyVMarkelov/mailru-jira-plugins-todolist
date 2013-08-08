// Created by Andrey Markelov 13-02-2013.
// Copyright Mail.Ru Group 2013. All rights reserved.

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
    var targetToDo = jQuery(target).parent().prev().find("span");

    section++;
    var textarea = '<div><textarea id=\"ddd' + section + '\">' + jQuery(targetToDo).html()+'</textarea>';
    var button = '<div><input type="button" value="SAVE" class="saveButton" /> <input type="button" value="CANCEL" class="cancelButton" /></div></div>';

    jQuery(targetToDo).after(textarea + button).hide();
    jQuery('.saveButton').click(function(){ru_mail_saveChanges(targetToDo, section, cfId);});
    jQuery('.cancelButton').click(function(){ru_mail_cancelChanges(targetToDo, section);});
}

//--> this function add todo
function ru_mail_addToDoItem(event, cfId, currtime) {
    var todoinput = jQuery("#todoinput").val();

    if (todoinput) {
        // check existing
        var sharesObj = jQuery.evalJSON(jQuery("#" + cfId).val());
        for (var objId in sharesObj) {
            if (sharesObj[objId]["id"] == todoinput) {
                jQuery("#todolist-main").animate({backgroundColor: "red"}, 500, function() { jQuery("#todolist-main").animate({backgroundColor: "white"}, 500);});
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
        ru_mail_setEditable();
    }
}

function ru_mail_saveChanges(obj, section, cfId) {
    var newvalue = jQuery('#ddd' + section).val();
    if (!newvalue) return;

    var targetToDo = jQuery(obj).text();
    var sharesObj = jQuery.evalJSON(jQuery("#" + cfId).val());
    for (var objId in sharesObj) {
        if (sharesObj[objId]["id"] == targetToDo) {
            sharesObj[objId]["id"] = newvalue;
            jQuery("#" + cfId).val(jQuery.toJSON(sharesObj));
        }
    }

    jQuery('#ddd' + section).parent().remove();
    jQuery(obj).html(newvalue).show();
}

function ru_mail_cancelChanges(obj, section) {
    jQuery('#ddd' + section).parent().remove();
    jQuery(obj).show();
}
