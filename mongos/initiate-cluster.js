rs.initiate({
    _id: "configReplSet",
    configsvr: true,
    members: [
        { _id: 0, host: "configsvr1:27017" },
        { _id: 1, host: "configsvr2:27017" },
        { _id: 2, host: "configsvr3:27017" }
    ]
});
sleep(5000);

conn = new Mongo("shard1-1:27017");
rs.initiate({
    _id: "shard1ReplSet",
    members: [
        { _id: 0, host: "shard1-1:27017" },
        { _id: 1, host: "shard1-2:27017" },
        { _id: 2, host: "shard1-3:27017" }
    ]
});
sleep(5000);

mongosConn = new Mongo("mongos:27017");
mongosConn.adminCommand({ addShard: "shard1ReplSet/shard1-1:27017,shard1-2:27017,shard1-3:27017" });
