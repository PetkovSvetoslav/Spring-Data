CREATE PROCEDURE find_all_books_by_the_names_of_the_author(f_name VARCHAR(30), l_name VARCHAR(40))
BEGIN
    SELECT *
    FROM books AS b
    WHERE b.author_id =
          (SELECT a.author_id FROM authors AS a WHERE a.first_name = f_name AND a.last_name = l_name);
END;
