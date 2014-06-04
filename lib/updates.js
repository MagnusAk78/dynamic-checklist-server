var duality_utils = require('duality/utils');

exports.edit_checkpoint = function(doc, req) {

    if(req.form.description &&
       req.form.checkpoint_name &&
       req.form.order_nr) {

        var newDoc;

        if(doc === null) {
            newDoc = {
                _id: req.uuid,
                type_of_obj: "checkpoint",
                active: true,
                order_nr: 0,
                time_days: 0,
                time_hours: 0,
                exclude_weekends: false
            };
        } else {
            newDoc = doc;
        }

        newDoc.checkpoint_name = req.form.checkpoint_name;
        newDoc.order_nr = parseInt(req.form.order_nr);
        newDoc.description = req.form.description;
        newDoc.time_days = parseInt(req.form.time_days);
        newDoc.time_hours = parseInt(req.form.time_hours);
        if(req.form.exclude_weekends === "on") {
            newDoc.exclude_weekends = true;
        }

        newDoc.error_tag_1 = "";
        newDoc.error_tag_2 = "";
        newDoc.error_tag_3 = "";
        newDoc.error_tag_4 = "";

        if(req.form.error_tag_1 !== "") {
            newDoc.error_tag_1 = req.form.error_tag_1;
        }
        if(req.form.error_tag_2 !== "") {
            newDoc.error_tag_2 = req.form.error_tag_2;
        }
        if(req.form.error_tag_3 !== "") {
            newDoc.error_tag_3 = req.form.error_tag_3;
        }
        if(req.form.error_tag_4 !== "") {
            newDoc.error_tag_4 = req.form.error_tag_4;
        }

        newDoc.action_tag_1 = "";
        newDoc.action_tag_2 = "";
        newDoc.action_tag_3 = "";
        newDoc.action_tag_4 = "";

        if(req.form.action_tag_1 !== "") {
            newDoc.action_tag_1 = req.form.action_tag_1;
        }
        if(req.form.action_tag_2 !== "") {
            newDoc.action_tag_2 = req.form.action_tag_2;
        }
        if(req.form.action_tag_3 !== "") {
            newDoc.action_tag_3 = req.form.action_tag_3;
        }
        if(req.form.action_tag_4 !== "") {
            newDoc.action_tag_4 = req.form.action_tag_4;
        }

        return [newDoc, duality_utils.redirect(req, '/edit_checkpoint_show/' + newDoc._id)]
    }

    return [newDoc, duality_utils.redirect(req, '/new_checkpoint_show')]
};

exports.activate_checkpoint = function(doc, req) {

    var docToActivate = doc;
    if(docToActivate) {
        docToActivate.active = true;
    }

    return [docToActivate, duality_utils.redirect(req, '/activate_checkpoints_list')]
};

exports.deactivate_checkpoint = function(doc, req) {

    var docToDeactivate = doc;
    if(docToDeactivate) {
        docToDeactivate.active = false;
    }

    return [docToDeactivate, duality_utils.redirect(req, '/deactivate_checkpoints_list')]
};
