-- noinspection SqlWithoutWhere
DELETE FROM book;

INSERT INTO book (isbn, title, author, description) VALUES ('978-0201633610', 'Design Patterns', 'Erich Gamma', 'Mit Design Patterns lassen sich wiederkehrende Aufgaben in der objektorientierten Softwareentwicklung effektiv lösen.');

INSERT INTO book (isbn, title, author, description) VALUES ('978-3826655487', 'Clean Code', 'Robert C. Martin', 'Das einzige praxisnahe Buch, mit dem Sie lernen, guten Code zu schreiben!');

INSERT INTO book (isbn, title, author, description) VALUES ('978-3836211161', 'Coding for Fun', 'Gottfried Wolmeringer', 'Dieses unterhaltsam geschriebene Buch führt Sie spielerisch durch die spektakuläre Geschichte unserer Blechkollegen.');
