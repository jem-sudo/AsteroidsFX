module com.score {
    requires spring.boot.starter.web;
    requires spring.beans;
    requires spring.context;
    requires spring.core;
    requires spring.web;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires spring.boot.autoconfigure;
    requires spring.boot;

    exports com.score;
    opens com.score to spring.core, spring.beans, spring.context;
} 