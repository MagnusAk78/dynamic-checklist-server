var templates = require('duality/templates');
var myutils = require('lib/myutils');

exports.view_checkpoints = function (head, req) {
    start({code: 200, headers: {'Content-Type': 'text/html'}});

    var row, checkpoints = [], index = -1;

    // fetch all the rows
    while (row = getRow()) {
        if(row.key[1] == '0') {
            checkpoints.push(row.value);
            index++;
        } else {
            checkpoints[index].last_measurement_time_string = new Date(row.key[1]).toString();
        }
    }

    var navigation = templates.render('navigation.html', req, {view_checkpoints: true});

    var content = templates.render('view_checkpoints.html', req, {rows: checkpoints});

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

exports.checkpoint_details = function (head, req) {

    start({code: 200, headers: {'Content-Type': 'text/html'}});

    var row, rows = [], checkpoint_name, date;

    // fetch all the rows
    while (row = getRow()) {
        date = row.key[1];
        if(date == '0') {
            checkpoint_name = row.value.name;
        } else {
            rows.push({
                date: date,
                measured_value: row.value.value,
                comma_sign: ','
            })
        }
    }

    if(rows.length > 1) {

        rows[rows.length-1].comma_sign = '';

        return templates.render('checkpoint_details.html', req, {
            checkpoint_name: checkpoint_name,
            rows: rows
        });

    } else {

        var navigation = templates.render('navigation.html', req, {});
        var content = templates.render('no_measurements.html', req, {checkpoint_name: checkpoint_name});

        return templates.render('base.html', req, {
            navigation: navigation,
            content: content
        });
    }
};

exports.checkpoint_edit = function (head, req) {
    start({code: 200, headers: {'Content-Type': 'text/html'}});

    var row, checkpoints = [];

    // fetch all the rows
    while (row = getRow()) {
        checkpoints.push(row.value);
    }

    var navigation = templates.render('navigation.html', req, {edit_checkpoints: true});

    var content = templates.render('edit_checkpoints.html', req, {rows: checkpoints});

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

exports.checkpoint_activate = function (head, req) {
    start({code: 200, headers: {'Content-Type': 'text/html'}});

    var row, checkpoints = [];

    // fetch all the rows
    while (row = getRow()) {
        checkpoints.push(row.value);
    }

    var navigation = templates.render('navigation.html', req, {activate_checkpoints: true});

    var content = templates.render('activate_checkpoints.html', req, {rows: checkpoints});

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

exports.checkpoint_deactivate = function (head, req) {
    start({code: 200, headers: {'Content-Type': 'text/html'}});

    var row, checkpoints = [];

    // fetch all the rows
    while (row = getRow()) {
        checkpoints.push(row.value);
    }

    var navigation = templates.render('navigation.html', req, {deactivate_checkpoints: true});

    var content = templates.render('deactivate_checkpoints.html', req, {rows: checkpoints});

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
