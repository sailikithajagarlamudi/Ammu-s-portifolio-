package com.library.dao;

import com.library.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbc;

    public MemberDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Member> findAll() {
        return jdbc.query("SELECT * FROM members ORDER BY id",
                (rs, rowNum) -> new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("credits")
                ));
    }

    public Member findById(int id) {
        List<Member> members = jdbc.query("SELECT * FROM members WHERE id = ?",
                (rs, rowNum) -> new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("credits")
                ), id);
        return members.isEmpty() ? null : members.get(0);
    }

    public int addCredits(int id, int credits) {
        return jdbc.update("UPDATE members SET credits = credits + ? WHERE id = ?", credits, id);
    }

    public int count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM members", Integer.class);
    }
}
