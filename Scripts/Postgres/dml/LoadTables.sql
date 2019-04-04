-- Prerequisite
    -- Create the tables before running the below script
-- Load the table "Items" with default excel provided
-- Edit the path in the line "6" with the "Users" csv file (Sample files are under dml/sample_csv_files)
-- Edit the path in the line "9" with the "Items" csv file (Sample files are under dml/sample_csv_files)

\COPY dbo."users"("user_name","password","phone_number") FROM '\path_to_users_list.csv' DELIMITER ',' CSV HEADER;

\COPY dbo."items"("name","description","rate") FROM '\path_to_items_list.csv' DELIMITER ',' CSV HEADER;
