DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS notofocations;
DROP TABLE IF EXISTS favoritePosts;
DROP TABLE IF EXISTS chats;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS reports;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS universitys;
DROP TABLE IF EXISTS categorys;
DROP TABLE IF EXISTS rules;
GO

CREATE TABLE universitys (
                             Id BIGINT IDENTITY(1,1) PRIMARY KEY,
                             universityName NVARCHAR(255) NOT NULL,
                             CONSTRAINT UQ_University_Name UNIQUE (universityName)
);
GO

CREATE TABLE courses (
                         Id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         courseName NVARCHAR(100) NOT NULL,
                         universityId BIGINT,
                         CONSTRAINT FK_Course_University FOREIGN KEY (universityId)
                             REFERENCES universitys(Id) ON DELETE SET NULL
);
GO

CREATE INDEX IDX_Course_University ON courses(universityId);
GO

CREATE TABLE categorys (
                           id BIGINT IDENTITY(1,1) PRIMARY KEY,
                           name NVARCHAR(150) NOT NULL,
                           CONSTRAINT UQ_Category_Name UNIQUE (name)
);
GO

CREATE TABLE rules (
                       Id BIGINT IDENTITY(1,1) PRIMARY KEY,
                       title NVARCHAR(200) NOT NULL,
                       content NVARCHAR(MAX) NOT NULL,
                       created DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

CREATE TABLE users (
                       Id BIGINT IDENTITY(1,1) PRIMARY KEY,
                       name NVARCHAR(255) NOT NULL,
                       lastName NVARCHAR(255) NOT NULL,
                       username NVARCHAR(50) NOT NULL,
                       email NVARCHAR(255) NOT NULL,
                       password NVARCHAR(500) NOT NULL,
                       age INT NOT NULL,
                       phoneNumber INT,
                       imageUrl NVARCHAR(500),
                       status NVARCHAR(50) DEFAULT 'ACTIVE',
                       badge BIT DEFAULT 0,
                       dateOfPriority DATETIME2,
                       reputation NVARCHAR(50),
                       role NVARCHAR(50) NOT NULL DEFAULT 'STUDENT',
                       gender NVARCHAR(10),
                       courseid BIGINT,
                       CONSTRAINT UQ_User_Email UNIQUE (email),
                       CONSTRAINT UQ_User_Username UNIQUE (username),
                       CONSTRAINT FK_User_Course FOREIGN KEY (courseid)
                           REFERENCES courses(Id) ON DELETE SET NULL,
                       CONSTRAINT CHK_User_Role CHECK (role IN ('ADMIN', 'STUDENT', 'QUESST')),
                       CONSTRAINT CHK_User_Gender CHECK (gender IN ('MALE', 'FEMALE'))
);
GO

CREATE INDEX IDX_User_Email ON users(email);
CREATE INDEX IDX_User_Username ON users(username);
CREATE INDEX IDX_User_Course ON users(courseid);
CREATE INDEX IDX_User_Role ON users(role);
GO

CREATE TABLE reports (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         content NVARCHAR(MAX) NOT NULL,
                         created DATETIME2 NOT NULL DEFAULT GETDATE(),
                         status NVARCHAR(50) DEFAULT 'PENDING',
                         userSender BIGINT NOT NULL,
                         reportedUser BIGINT NOT NULL,
                         CONSTRAINT FK_Report_Sender FOREIGN KEY (userSender)
                             REFERENCES users(Id) ON DELETE NO ACTION,
                         CONSTRAINT FK_Report_Reported FOREIGN KEY (reportedUser)
                             REFERENCES users(Id) ON DELETE NO ACTION,
                         CONSTRAINT CHK_Report_Status CHECK (status IN ('PENDING', 'RESOLVED', 'DISMISSED')),
                         CONSTRAINT CHK_Report_DifferentUsers CHECK (userSender != reportedUser)
    );
GO

CREATE INDEX IDX_Report_Sender ON reports(userSender);
CREATE INDEX IDX_Report_Reported ON reports(reportedUser);
CREATE INDEX IDX_Report_Status ON reports(status);
CREATE INDEX IDX_Report_Created ON reports(created);
GO

CREATE TABLE posts (
                       id BIGINT IDENTITY(1,1) PRIMARY KEY,
                       title NVARCHAR(200) NOT NULL,
                       content NVARCHAR(500) NOT NULL,
                       imageUrl NVARCHAR(500),
                       price SMALLINT NOT NULL,
                       status NVARCHAR(50) DEFAULT 'PENDING',
                       created DATETIME2 NOT NULL DEFAULT GETDATE(),
                       userId BIGINT NOT NULL,
                       categoryId BIGINT NOT NULL,
                       administratorid BIGINT,
                       buyerId BIGINT,
                       CONSTRAINT FK_Post_User FOREIGN KEY (userId)
                           REFERENCES users(Id) ON DELETE CASCADE,
                       CONSTRAINT FK_Post_Administrator FOREIGN KEY (administratorid)
                           REFERENCES users(Id) ON DELETE NO ACTION,
                       CONSTRAINT FK_Post_Buyer FOREIGN KEY (buyerId)
                           REFERENCES users(Id) ON DELETE NO ACTION,
                       CONSTRAINT FK_Post_Category FOREIGN KEY (categoryId)
                           REFERENCES categorys(id) ON DELETE NO ACTION,
                       CONSTRAINT CHK_Post_Price CHECK (price >= 0),
                       CONSTRAINT CHK_Post_Status CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED', 'SOLD', 'DONATE'))
);
GO

CREATE INDEX IDX_Post_User ON posts(userId);
CREATE INDEX IDX_Post_Category ON posts(categoryId);
CREATE INDEX IDX_Post_Status ON posts(status);
CREATE INDEX IDX_Post_Created ON posts(created);
GO

CREATE TABLE chats (
                       id BIGINT IDENTITY(1,1) PRIMARY KEY,
                       adId BIGINT NOT NULL,
                       buyerId BIGINT NOT NULL,
                       CONSTRAINT FK_Chat_Post FOREIGN KEY (adId)
                           REFERENCES posts(id) ON DELETE CASCADE,
                       CONSTRAINT FK_Chat_Buyer FOREIGN KEY (buyerId)
                           REFERENCES users(Id) ON DELETE NO ACTION
);
GO

CREATE INDEX IDX_Chat_Post ON chats(adId);
CREATE INDEX IDX_Chat_Buyer ON chats(buyerId);
CREATE UNIQUE INDEX UQ_Chat_Post_Buyer ON chats(adId, buyerId);
GO

CREATE TABLE messages (
                          id BIGINT IDENTITY(1,1) PRIMARY KEY,
                          content NVARCHAR(MAX) NOT NULL,
                          seen BIT DEFAULT 0,
                          sendingDate DATETIME2 NOT NULL DEFAULT GETDATE(),
                          chatId BIGINT NOT NULL,
                          senderId BIGINT NOT NULL,
                          CONSTRAINT FK_Message_Chat FOREIGN KEY (chatId)
                              REFERENCES chats(id) ON DELETE CASCADE,
                          CONSTRAINT FK_Message_Sender FOREIGN KEY (senderId)
                              REFERENCES users(Id) ON DELETE NO ACTION
);
GO

CREATE INDEX IDX_Message_Chat ON messages(chatId);
CREATE INDEX IDX_Message_Sender ON messages(senderId);
CREATE INDEX IDX_Message_SendingDate ON messages(sendingDate);
CREATE INDEX IDX_Message_Seen ON messages(seen);
GO

CREATE TABLE notofocations (
                               id BIGINT IDENTITY(1,1) PRIMARY KEY,
                               content NVARCHAR(MAX) NOT NULL,
                               seen BIT DEFAULT 0,
                               date DATETIME2 NOT NULL DEFAULT GETDATE(),
                               type NVARCHAR(50) NOT NULL,
                               userId BIGINT NOT NULL,
                               chatId BIGINT,
                               CONSTRAINT FK_Notification_User FOREIGN KEY (userId)
                                   REFERENCES users(Id) ON DELETE CASCADE,
                               CONSTRAINT FK_Notification_Chat FOREIGN KEY (chatId)
                                   REFERENCES chats(id) ON DELETE NO ACTION,
                               CONSTRAINT CHK_Notification_Type CHECK (type IN ('MESSAGE', 'POST_APPROVMENT', 'POST_REJECTED', 'PURCHASE_CONFIRMATION'))
);
GO

CREATE INDEX IDX_Notification_User ON notofocations(userId);
CREATE INDEX IDX_Notification_Chat ON notofocations(chatId);
CREATE INDEX IDX_Notification_Seen ON notofocations(seen);
CREATE INDEX IDX_Notification_Date ON notofocations(date);
CREATE INDEX IDX_Notification_Type ON notofocations(type);
GO

CREATE TABLE favoritePosts (
                               id BIGINT IDENTITY(1,1) PRIMARY KEY,
                               date DATETIME2 NOT NULL DEFAULT GETDATE(),
    [user] BIGINT NOT NULL,
                               adid BIGINT NOT NULL,
                               CONSTRAINT FK_FavoritePost_User FOREIGN KEY ([user])
                                   REFERENCES users(Id) ON DELETE CASCADE,
                               CONSTRAINT FK_FavoritePost_Post FOREIGN KEY (adid)
                                   REFERENCES posts(id) ON DELETE NO ACTION
);
GO

CREATE INDEX IDX_FavoritePost_User ON favoritePosts([user]);
CREATE INDEX IDX_FavoritePost_Post ON favoritePosts(adid);
CREATE UNIQUE INDEX UQ_FavoritePost_User_Post ON favoritePosts([user], adid);
GO