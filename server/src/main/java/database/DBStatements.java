package database;

public enum DBStatements implements StatementHoldable {
    SELECT_ALL ("select * from priorityQueue"),
    ADD_ROUTE ("""
            insert into priorityQueue \
            (id, coordinateX, coordinateY, creationDate, distance, locationToX, locationToY, \
            locationToZ, locationToName, locationFromX, locationFromY, locationFromZ, locationFromName, name, author) \
            values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """
    ),
    CLEAR_ALL_BY_USER ("delete from priorityQueue where author = ?"),
    DELETE_BY_ID ("delete from priorityQueue where id = ?"),
    UPDATE_BY_ID ("""
            update priorityQueue set \
            id=?, coordinateX=?, coordinateY=?, creationDate=?, distance=?, locationToX=?, locationToY=?, \
            locationToZ=?, locationToName=?, locationFromX=?, locationFromY=?, locationFromZ=?, \
            locationFromName=?, name=?, author=? where id = ?
            """
    ),
    CREATE_IF_NOT_EXIST ("""
            create sequence if not exists ids start 1;
            create table if not exists priorityQueue (\
            id int PRIMARY KEY,\
            coordinateX int not null, coordinateY int not null check(coordinateY < 106 and coordinateY >= 0),\
            creationDate date default (current_date),\
            distance bigint not null,\
            locationToX bigint not null,\
            locationToY int not null,\
            locationToZ bigint not null,\
            locationToName varchar(255),\
            locationFromX bigint,\
            locationFromY int,\
            locationFromZ bigint,\
            locationFromName varchar(255),\
            name varchar(255) not null,\
            author varchar(255) not null);
            create table if not exists users (\
            username varchar(255) PRIMARY KEY,\
            hashPassword BYTEA DEFAULT (null)\
            );
            """),
    GENERATE_ID("select nextval('ids')"),
    SELECT_USER("select * from users where username=?"),
    ADD_USER("insert into users (username, hashPassword) values(?,?)"),
    ;
    private final String stmt;
    DBStatements(String stmt) { this.stmt = stmt; }

    public String getStatement() {
        return stmt;
    }
}
