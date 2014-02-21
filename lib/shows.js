var templates = require('duality/templates');

exports.edit_checkpoint = function (doc, req) {

    var contentValues = {
        db_name: req.info.db_name
    }

    var navigation;

    if(doc) {
        contentValues.doc = doc;

        switch(doc.updates) {
            case 1:
                contentValues.updates_1 = true;
                break;
            case 2:
                contentValues.updates_2 = true;
                break;
            case 3:
                contentValues.updates_3 = true;
                break;
            case 4:
                contentValues.updates_4 = true;
                break;
            case 5:
                contentValues.updates_5 = true;
                break;
            case 6:
                contentValues.updates_6 = true;
                break;
            default:
                contentValues.updates_7 = true;
        }

        switch(doc.time_period) {
            case "hour":
                contentValues.time_period_hour = true;
                break;
            case "day":
                contentValues.time_period_day = true;
                break;
            case "week":
                contentValues.time_period_week = true;
                break;
            default:
                contentValues.time_period_month = true;
        }

        switch(doc.start_time) {
            case 1:
                contentValues.start_time_1 = true;
                break;
            case 2:
                contentValues.start_time_2 = true;
                break;
            case 3:
                contentValues.start_time_3 = true;
                break;
            case 4:
                contentValues.start_time_4 = true;
                break;
            case 5:
                contentValues.start_time_5 = true;
                break;
            case 6:
                contentValues.start_time_6 = true;
                break;
            case 7:
                contentValues.start_time_7 = true;
                break;
            case 8:
                contentValues.start_time_8 = true;
                break;
            case 9:
                contentValues.start_time_9 = true;
                break;
            case 10:
                contentValues.start_time_10 = true;
                break;
            case 11:
                contentValues.start_time_11 = true;
                break;
            case 12:
                contentValues.start_time_12 = true;
                break;
            case 13:
                contentValues.start_time_13 = true;
                break;
            case 14:
                contentValues.start_time_14 = true;
                break;
            case 15:
                contentValues.start_time_15 = true;
                break;
            case 16:
                contentValues.start_time_16 = true;
                break;
            case 17:
                contentValues.start_time_17 = true;
                break;
            case 18:
                contentValues.start_time_18 = true;
                break;
            case 19:
                contentValues.start_time_19 = true;
                break;
            case 20:
                contentValues.start_time_20 = true;
                break;
            case 21:
                contentValues.start_time_21 = true;
                break;
            case 22:
                contentValues.start_time_22 = true;
                break;
            case 23:
                contentValues.start_time_23 = true;
                break;
            default:
                contentValues.start_time_0 = true;
        }

        switch(doc.start_day) {
            case 1:
                contentValues.start_day_1 = true;
                break;
            case 2:
                contentValues.start_day_2 = true;
                break;
            case 3:
                contentValues.start_day_3 = true;
                break;
            case 4:
                contentValues.start_day_4 = true;
                break;
            case 5:
                contentValues.start_day_5 = true;
                break;
            case 6:
                contentValues.start_day_6 = true;
                break;
            default:
                contentValues.start_day_7 = true;
        }

        contentValues.doc.start_date_sec = doc.start_date / 1000;
        if(doc._attachments) {
            contentValues.attached_image = Object.keys(doc._attachments)[0];
        }
        navigation = templates.render('navigation.html', req, {
            edit_checkpoints: true
        });
    } else {
        contentValues.newCheckpoint = true;
        navigation = templates.render('navigation.html', req, {
            add_checkpoint: true
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
