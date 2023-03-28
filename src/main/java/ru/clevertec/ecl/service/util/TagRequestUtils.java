package ru.clevertec.ecl.service.util;

public final class TagRequestUtils {

    public static final String CREATE_TAG_SQL = "INSERT INTO clev.tags(\n" +
            "\tcreate_date, update_date, name)\n" +
            "\tVALUES (?, ?, ?)" +
            "RETURNING *;";

    public static final String GET_TAG_BY_ID_SQL = "SELECT id, create_date, update_date, name " +
            "FROM clev.tags " +
            "WHERE clev.tags.id = ?;";

    public static final String GET_ALL_TAGS_WITH_LIMIT_OFFSET_SQL = "SELECT id, create_date, update_date, name " +
            "FROM clev.tags " +
            "LIMIT ?" +
            "OFFSET ?;";

    public static final String UPDATE_TAG_SQL = "UPDATE clev.tags " +
            "SET create_date = ?, update_date = ?, name = ?" +
            "WHERE id = ?" +
            "RETURNING *;";

    public static final String DELETE_TAG_BY_ID = "DELETE FROM clev.tags " +
            "WHERE id = ?";

    public static final String IS_EXISTS_TAG_BY_NAME = "SELECT id, create_date, update_date, name " +
            "FROM clev.tags " +
            "WHERE name = ? " +
            "FETCH FIRST ROWS ONLY;";

}
