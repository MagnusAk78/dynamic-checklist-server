exports.checkpoints = {
    map: function (doc) {
        if(doc.type_of_obj == "checkpoint" && doc.active) {
            emit([doc.order_nr, doc._id], doc);
        }
    }
};

exports.checkpoints_deactivated = {
    map: function (doc) {
        if(doc.type_of_obj == "checkpoint" && !doc.active) {
            emit([doc.order_nr, doc._id], doc);
        }
    }
};

exports.checkpoints_all = {
    map: function (doc) {
        if(doc.type_of_obj == "checkpoint") {
            emit([doc.order_nr, doc._id], doc);
        }
    }
};

exports.measurements = {
    map: function (doc) {
        if(doc.type_of_obj == "measurement") {
            emit([doc.checkpoint, doc.date], doc);
        }
    }
};

exports.checkpoints_and_measurement = {
    map: function (doc) {
        if(doc.type_of_obj == "checkpoint" && doc.active) {
            emit([doc._id, 0], doc);
        } else if(doc.type_of_obj == "measurement") {
            emit([doc.checkpoint, doc.date], doc);
        }
    }
};
