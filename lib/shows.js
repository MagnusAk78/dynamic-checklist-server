var templates = require('duality/templates');

exports.edit_checkpoint = function (doc, req) {

    var contentValues = {
        db_name: req.info.db_name
    }

    var navigation;

    if(doc) {
        contentValues.doc = doc;

        contentValues.doc.start_date_sec = doc.start_date / 1000;
        if(doc._attachments) {
            contentValues.attached_image = Object.keys(doc._attachments)[0];
        }
        navigation = templates.render('navigation.html', req, {
            edit_checkpoints: true,
            database_name: req.path[0]
        });
    } else {
        contentValues.newCheckpoint = true;
        navigation = templates.render('navigation.html', req, {
            add_checkpoint: true,
            database_name: req.path[0]
        });
    }

    var content = templates.render('edit_checkpoint.html', req, contentValues);

    if (req.client) {
        // being run client-side, update the current page
        $('#navigation').html(navigation);
        $('#content').html(content);
    } else {
        // being run server-side, return a complete rendered page
        return templates.render('base.html', req, {
            navigation: navigation,
            content: content
        });
    }
};
