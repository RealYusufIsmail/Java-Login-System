/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, YusufIsmail
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package io.github.realyusufismail.datbase;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.yusufsdiscordbot.annotations.Author;
import io.github.yusufsdiscordbot.annotations.Credits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Were the database file is registered and created. The file location for the database is stored in
 * this class and the process of creating the database file is coded here.
 *
 * @author Yusuf Arfan Ismail
 */
@Author(firstName = "Yusuf", lastName = "Arfan Ismail", githubUserName = "RealYusufIsmail")
//Taken from my discord bot.
@Credits(source = "https://github.com/YusufsDiscordbot/Yusuf-s-Moderation-Bot/blob/JDA-Development/database/src/main/java/io/github/yusufsdiscordbot/yusufsmoderationbot/DataBase.java")
public class DataBase {
    private static final Logger logger = LoggerFactory.getLogger(DataBase.class);
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        try {
            final File dbFile = new File(DataBaseConfig.getDataBaseFileLocation());

            if (!dbFile.exists()) {
                if (dbFile.createNewFile()) {
                    logger.info("Created database file");
                } else {
                    logger.info("Could not create database file");
                }
            }
        } catch (IOException e) {
            logger.error("Error while loading database", e);
        }

        config.setJdbcUrl(DataBaseConfig.getDataBaseUrl());
        config.setUsername("");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        dataSource = new HikariDataSource(config);

        try (final Statement statement = getConnection().createStatement()) {
            File folder = new File(DataBaseConfig.getDataBaseSQLFolderLocation());
            File[] listOfFiles = folder.listFiles();
            logger.info("Checking database for tables");

            for (File file : Objects.requireNonNull(listOfFiles)) {
                if (file.isFile()) {
                    statement.execute(new String(Files.readAllBytes(Path.of(file.getPath()))));
                }
            }
            logger.info("Database table created");
            statement.closeOnCompletion();
            logger.info("Database table closed");
        } catch (SQLException | IOException e) {
            logger.error("Error while loading database", e);
        }
    }

    private DataBase() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
