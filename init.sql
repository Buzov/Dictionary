CREATE TABLE part_of_speech (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name_en NVARCHAR(50) NOT NULL,
  name_ru NVARCHAR(50) NOT NULL
);

CREATE TABLE category (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name NVARCHAR(50) NOT NULL
);

CREATE TABLE state (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name NVARCHAR(50) NOT NULL
);

CREATE TABLE words (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  word NVARCHAR(50) NOT NULL,
  transcription NVARCHAR(50) NOT NULL,
  translation NVARCHAR(50) NOT NULL,
  part_of_speech INTEGER,
  category INTEGER,
  state INTEGER,
  status BOOLEAN NOT NULL,
  sound BLOB,
  image BLOB,
  FOREIGN KEY(part_of_speech) REFERENCES part_of_speech(_id),
  FOREIGN KEY(category) REFERENCES category(_id),
  FOREIGN KEY(state) REFERENCES state(_id)
);

CREATE TABLE "android_metadata" ("locale" TEXT DEFAULT 'en_US')

INSERT INTO "android_metadata" VALUES ('en_US')



INSERT INTO part_of_speech VALUES (1, 'Noun', 'Существительное')
INSERT INTO part_of_speech VALUES (2, 'Pronoun', 'Местоимение')
INSERT INTO part_of_speech VALUES (3, 'Verb', 'Глагол')
INSERT INTO part_of_speech VALUES (4, 'Adjective', 'Прилагательное')
INSERT INTO part_of_speech VALUES (5, 'Adverb', 'Наречие')
INSERT INTO part_of_speech VALUES (6, 'Number', 'Числительное')
INSERT INTO part_of_speech VALUES (7, 'Preposition', 'Предлог')
INSERT INTO part_of_speech VALUES (8, 'Conjunction', 'Союз')
INSERT INTO part_of_speech VALUES (9, 'Interjection', 'Междометие')
