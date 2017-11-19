//This makes use of layersdb connection that is used by layers-store
dataSource {
    pooled = true
    driverClassName = "org.postgresql.Driver"
    dialect = org.hibernate.dialect.PostgreSQL9Dialect
    dbCreate = "update" // one of 'create', 'update', 'validate', ''
    url = "jdbc:postgresql://localhost/layersdb"
    username = "postgres"
    password = "postgres"
}

environments {
    development {
        dataSource {
//            logSql = true

        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            logSql = false
        }
    }
}
