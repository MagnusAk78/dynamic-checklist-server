var templates = require('duality/templates');

var SECOND = 1000;
var MINUTE = SECOND * 60;
var HOUR = MINUTE * 60;
var DAY = HOUR * 24;
var WEEK = DAY * 7;

exports.SECOND = SECOND;
exports.MINUTE = MINUTE;
exports.HOUR = HOUR;
exports.DAY = DAY;
exports.WEEK = WEEK;

var millis_to_time_period_text = function(milliseconds) {

    var reduce_millis = function(milliseconds, time_period) {
        var nr = 0, millis_rest = milliseconds;
        while(millis_rest >= time_period) {
            millis_rest -= time_period;
            nr++;
        }
        return [millis_rest, nr]
    };

    var singular_plural_text = function(nr, text, trailing) {
        if(nr == 1) {
            return nr + ' ' + text + trailing;
        } else if(nr > 1) {
            return nr + ' ' + text + 's' + trailing;
        } else {
            return '';
        }
    };

    var weeks_res = reduce_millis(milliseconds, WEEK);
    var days_res = reduce_millis(weeks_res[0], DAY);
    var hours_res = reduce_millis(days_res[0], HOUR);
    var minutes_res = reduce_millis(hours_res[0], MINUTE);

    return '' +
        singular_plural_text(weeks_res[1], 'week', ', ') +
        singular_plural_text(days_res[1], 'day', ', ') +
        singular_plural_text(hours_res[1], 'hour', ', ') +
        singular_plural_text(minutes_res[1], 'minute', '');
};

exports.millis_to_time_period_text = millis_to_time_period_text;
