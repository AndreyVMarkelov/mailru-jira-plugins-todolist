
function ru_mail_addTodo(cfId) {
    var todoinput = jQuery("#todoinput").val();

    if (todoinput) {
        var sharesObj = jQuery.evalJSON(jQuery("#cfId").val());
        for (var objId in sharesObj) {
            if (sharesObj[objId]["id"] == todoinput) {
                jQuery("#todolist-main").animate({backgroundColor: "red"}, 500, function() { jQuery("#todolist-main").animate({backgroundColor: "white"}, 500);});
                return;
            }
        }

        jQuery("#items").append("<tr><td><input class='de1' type='checkbox' value='ff'/></td><td><span>" + todoinput + "</span></td><td><a href='#' class='de'/></td></tr>");
        jQuery("#$customField.id").val("dewdw");
        jQuery(".de1").change(function() {
            var sharesObj1 = jQuery.evalJSON(jQuery("#$customField.id").val());
            var tx = jQuery(this).parent().next().find("span").text();
            for (var objId in sharesObj) {
                if (sharesObj[objId]["id"] == tx) {
                    if (this.checked) {
                        sharesObj[objId]["type"] = 'done';
                    } else {
                        sharesObj[objId]["type"] = 'todo';
                    }
                    jQuery("#$customField.id").val(jQuery.toJSON(sharesObj));
                }
            }

            if (this.checked) {
                jQuery(this).parent().next().children().css({'color': 'red', 'text-decoration': 'line-through'});
            } else {
                jQuery(this).parent().next().children().css({'color': 'black', 'text-decoration': 'none'});
            }
        });

        jQuery(".de").click(function(e) {
            jQuery(this).parent().parent().remove();
        });
        jQuery("#todoinput").val("");

        var itemObj = new Object();
        itemObj["id"] = todoinput;
        itemObj["type"] = "todo";
        sharesObj.push(itemObj);
        jQuery("#$customField.id").val(jQuery.toJSON(sharesObj));
    }
}

jQuery("#addtodoitem").click(function (e) {
        var todoinput = jQuery("#todoinput").val();
        if (todoinput) {
            var sharesObj = jQuery.evalJSON(jQuery("#$customField.id").val());
            for (var objId in sharesObj) {
                if (sharesObj[objId]["id"] == todoinput) {
                    jQuery("#todolist-main").animate({backgroundColor: "red"}, 500, function() { jQuery("#todolist-main").animate({backgroundColor: "white"}, 500);});
                    return;
                }
            }

            jQuery("#items").append("<tr><td><input class='de1' type='checkbox' value='ff'/></td><td><span>" + todoinput + "</span></td><td><a href='#' class='de'/></td></tr>");
            jQuery("#$customField.id").val("dewdw");
            jQuery(".de1").change(function() {
                var sharesObj1 = jQuery.evalJSON(jQuery("#$customField.id").val());
                var tx = jQuery(this).parent().next().find("span").text();
                for (var objId in sharesObj) {
                    if (sharesObj[objId]["id"] == tx) {
                        if (this.checked) {
                            sharesObj[objId]["type"] = 'done';
                        } else {
                            sharesObj[objId]["type"] = 'todo';
                        }
                        jQuery("#$customField.id").val(jQuery.toJSON(sharesObj));
                    }
                }

                if (this.checked) {
                    jQuery(this).parent().next().children().css({'color': 'red', 'text-decoration': 'line-through'});
                } else {
                    jQuery(this).parent().next().children().css({'color': 'black', 'text-decoration': 'none'});
                }
            });

            jQuery(".de").click(function(e) {
                jQuery(this).parent().parent().remove();
            });
            jQuery("#todoinput").val("");

            var itemObj = new Object();
            itemObj["id"] = todoinput;
            itemObj["type"] = "todo";
            sharesObj.push(itemObj);
            jQuery("#$customField.id").val(jQuery.toJSON(sharesObj));
        }
    });