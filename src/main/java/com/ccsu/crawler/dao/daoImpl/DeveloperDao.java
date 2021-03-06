package com.ccsu.crawler.dao.daoImpl;

import com.ccsu.crawler.dao.Dao;
import com.ccsu.crawler.model.Developer;

import java.sql.*;
import java.util.Map;

import com.ccsu.crawler.utils.MysqlConnect;

public class DeveloperDao implements Dao<Developer> {

    private Connection connection;

    @Override
    public int insert(Developer developer){
        int count = 0;
        try {
            connection = MysqlConnect.getConnect();
            String INSERT_SQL = "insert into tb_developer " +
                    "(id,login,avatar_url,name,company,location,blog,email,bio,type,public_repos," +
                    "followers, following,created_at,updated_at) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
            ps.setLong(1,developer.getId());
            ps.setString(2,developer.getLogin());
            ps.setString(3,developer.getAvatarUrl());
            ps.setString(4,developer.getName());
            ps.setString(5,developer.getCompany());
            ps.setString(6,developer.getLocation());
            ps.setString(7,developer.getBlog());
            ps.setString(8,developer.getEmail());
            ps.setString(9,developer.getBio());
            ps.setString(10,developer.getType());
            ps.setInt(11,developer.getPublicRepos() == null?0:developer.getPublicRepos());
            ps.setInt(12,developer.getFollowers() == null?0:developer.getFollowers());
            ps.setInt(13,developer.getFollowing() == null?0:developer.getFollowing());
            ps.setTimestamp(14,new Timestamp(developer.getCreatedAt() ==  null?0:developer.getCreatedAt().getTime()));
            ps.setTimestamp(15,new Timestamp(developer.getUpdatedAt() ==  null?0:developer.getUpdatedAt().getTime()));
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public Developer select(Map map) {
        Long id = (Long) map.get("id");
        ResultSet rs;
        Developer developer = null;
        try {
            connection = MysqlConnect.getConnect();
            String SELECT_SQL = "select * from tb_developer where id = ?";
            PreparedStatement ps = connection.prepareStatement(SELECT_SQL);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            while (rs.next()){
                developer = new Developer();
                developer.setId(rs.getLong(1));
                developer.setLogin(rs.getString(2));
                developer.setAvatarUrl(rs.getString(3));
                developer.setName(rs.getString(4));
                developer.setCompany(rs.getString(5));
                developer.setLocation(rs.getString(6));
                developer.setBlog(rs.getString(7));
                developer.setEmail(rs.getString(8));
                developer.setBio(rs.getString(9));
                developer.setType(rs.getString(10));
                developer.setPublicRepos(rs.getInt(11));
                developer.setFollowers(rs.getInt(12));
                developer.setFollowing(rs.getInt(13));
                developer.setCreatedAt(rs.getDate(14));
                developer.setUpdatedAt(rs.getDate(15));
                developer.setUpdated(rs.getDate(16));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return developer;
    }

    @Override
    public int update(Map map) {
        return 0;
    }

}
