BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `Tag` (
	`idTag`	INTEGER,
	`descrizione`	TEXT,
	PRIMARY KEY(`idTag`)
);
CREATE TABLE IF NOT EXISTS `LezioneTag` (
	`idLezione`	INTEGER,
	`idTag`	INTEGER,
	FOREIGN KEY(`idTag`) REFERENCES `Tag`(`idTag`),
	FOREIGN KEY(`idLezione`) REFERENCES `Lezione`(`idLezione`),
	PRIMARY KEY(`idLezione`,`idTag`)
);
CREATE TABLE IF NOT EXISTS `LezioneFile` (
	`idFile`	INTEGER,
	`IdLezione`	INTEGER,
	FOREIGN KEY(`idFile`) REFERENCES `File`(`idFile`),
	PRIMARY KEY(`idFile`,`IdLezione`),
	FOREIGN KEY(`IdLezione`) REFERENCES `Lezione`(`idLezione`)
);
CREATE TABLE IF NOT EXISTS `Lezione` (
	`idLezione`	INTEGER,
	`data`	TEXT NOT NULL,
	`anno`	INTEGER NOT NULL,
	`numero`	INTEGER NOT NULL,
	`nome`	TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS `File` (
	`idFile`	INTEGER,
	`nome`	TEXT NOT NULL,
	`path`	TEXT NOT NULL,
	PRIMARY KEY(`idFile`)
);
COMMIT;