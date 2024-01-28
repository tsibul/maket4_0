package ru.maket.maket4_0.db.dictionary.color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class ColorSchemeService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ColorSchemeService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ColorScheme getColorSchemeById(Long colorSchemeId) {
        String colorSchemeSelect = "SELECT id, deleted, name, public_name FROM color_scheme WHERE id =" + colorSchemeId;
        return jdbcTemplate.queryForObject(colorSchemeSelect, new BeanPropertyRowMapper<>(ColorScheme.class));
    }
}
