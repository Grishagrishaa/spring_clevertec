package ru.clevertec.ecl.service.util;

import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;

public final class GCRequestUtils {
    private static final StringBuffer strBuffer = new StringBuffer();

    public static final String CREATE_GC_SQL = "INSERT INTO clev.gift_certificates(\n" +
            "\tcreate_date, update_date, name, description, price, duration)\n" +
            "\tVALUES (?, ? , ?, ?, ?, ?)" +
            "RETURNING *;";

    public static final String GET_GC_BY_ID_SQL = "SELECT id, create_date, update_date, name, description, price, duration " +
            "FROM clev.gift_certificates " +
            "WHERE clev.gift_certificates.id = ?;";

    public static final String UPDATE_GC_SQL = "UPDATE clev.gift_certificates " +
            "SET create_date = ?, update_date = ?, name = ?, description = ?, price = ?, duration = ?" +
            "WHERE clev.gift_certificates.id = ?" +
            "RETURNING *;";

    public static final String DELETE_GC_BY_ID = "DELETE FROM clev.gift_certificates " +
            "WHERE clev.gift_certificates.id = ?";

    public static final String ADD_TAGS_ASSOCIATION_SQL = "INSERT INTO clev.gift_certificates_tags " +
            "VALUES (?, ?);";

    public static final String DELETE_TAGS_ASSOCIATION_BY_GC_ID_SQL = "DELETE FROM clev.gift_certificates_tags " +
            "WHERE gift_certificates_tags.gift_certificate_id = ?";

    public static final String GET_ASSOCIATED_TAGS_BY_GC_ID = "SELECT id, create_date, update_date, name " +
            "FROM clev.tags INNER JOIN clev.gift_certificates_tags ON gift_certificates_tags.gift_certificate_id = ?" +
            "AND gift_certificates_tags.tag_id = id";

    public static final String FILTERED_JOIN_REQUEST = "SELECT clev.gift_certificates.id, clev.gift_certificates.name, description, price, duration, \n" +
            "clev.gift_certificates.create_date, clev.gift_certificates.update_date\n" +
            "FROM clev.gift_certificates \n" +
            "INNER JOIN clev.gift_certificates_tags ON clev.gift_certificates.id = clev.gift_certificates_tags.gift_certificate_id \n" +
            "INNER JOIN clev.tags ON clev.tags.id = clev.gift_certificates_tags.tag_id \n";

    public static final String FILTERED_REQUEST = "SELECT id, name, description, price, duration, \n" +
            "create_date, update_date\n" +
            "FROM clev.gift_certificates \n";



    public static String getFilteredRequest(GiftCertificateFilter filter){
        strBuffer.setLength(0);

        if (!filter.getTagName().isEmpty()){
            strBuffer.append("WHERE clev.tags.name = '").append(filter.getTagName()).append("' ");
        }

        if(!filter.getName().isEmpty()){
            if(strBuffer.isEmpty()){
                strBuffer.append("WHERE ");
            }else {
                strBuffer.append("AND ");
            }
            strBuffer.append("clev.gift_certificates.name LIKE '%").append(filter.getName()).append("%' ");
        }

        if(!filter.getDescription().isEmpty()){
            if(strBuffer.isEmpty()){
                strBuffer.append("WHERE ");
            }else {
                strBuffer.append("AND ");
            }
            strBuffer.append("clev.gift_certificates.description LIKE '%").append(filter.getDescription()).append("%' ");
        }

        strBuffer.append("\n LIMIT ? OFFSET ?");
        return filter.getTagName().isEmpty() ? FILTERED_REQUEST + strBuffer : FILTERED_JOIN_REQUEST + strBuffer;
    }

}
