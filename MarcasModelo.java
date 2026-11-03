package Polizas.Marcas;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import Polizas.General.Conexion;
import Polizas.General.DAO;

public class MarcasModelo extends Conexion implements DAO {
    public MarcasModelo() {
        super();
    }

    public boolean existeMarca(String nombre) {
        boolean existe = false;
        try {
            this.conectar();
            ps = cnx.prepareStatement("SELECT COUNT(*) FROM marca WHERE nombreMarca = ?");
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try { if (cnx != null) cnx.close(); } catch (SQLException ex) {}
        }
        return existe;
    }

    public List<Marca> read(String filtro) {
        List<Marca> lista = new ArrayList<>();
        try {
            this.conectar();
            String sql = "SELECT idMarca, nombreMarca FROM marca";
            if (filtro != null && !filtro.trim().isEmpty()) {
                sql += " WHERE nombreMarca LIKE ? OR idMarca = ?";
            }
            sql += " ORDER BY idMarca ASC";

            ps = cnx.prepareStatement(sql);
            if (filtro != null && !filtro.trim().isEmpty()) {
                ps.setString(1, "%" + filtro + "%");
                try {
                    ps.setInt(2, Integer.parseInt(filtro));
                } catch (NumberFormatException e) {
                    ps.setInt(2, -1);
                }
            }
            
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Marca(rs.getInt("idMarca"), rs.getString("nombreMarca")));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try { if (cnx != null) cnx.close(); } catch (SQLException ex) {}
        }
        return lista;
    }

    @Override
    public List<Marca> read() {
        return read("");
    }

    @Override
    public int create(Object obj) {
        int resultado = 0;
        try {
            this.conectar();
            Marca marca = (Marca) obj;
            ps = cnx.prepareStatement("INSERT INTO marca (nombreMarca) VALUES (?)");
            ps.setString(1, marca.getNombre());
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try { if (cnx != null) cnx.close(); } catch (SQLException ex) {}
        }
        return resultado;
    }

    @Override
    public int update(Object obj) {
        int resultado = 0;
        try {
            this.conectar();
            Marca marca = (Marca) obj;
            ps = cnx.prepareStatement("UPDATE marca SET nombreMarca = ? WHERE idMarca = ?");
            ps.setString(1, marca.getNombre());
            ps.setInt(2, marca.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try { if (cnx != null) cnx.close(); } catch (SQLException ex) {}
        }
        return resultado;
    }

    @Override
    public int delete(Object obj) {
        int resultado = 0;
        try {
            this.conectar();
            Marca marca = (Marca) obj;
            
         
            ps = cnx.prepareStatement("DELETE FROM marca WHERE idMarca = ?");
            ps.setInt(1, marca.getId());
            resultado = ps.executeUpdate();
            
           
            if (resultado > 0) {
                Statement stmt = cnx.createStatement();
                stmt.executeUpdate("SET @i = 0;");
                stmt.executeUpdate("UPDATE marca SET idMarca = (@i := @i + 1) ORDER BY idMarca ASC;");
                stmt.executeUpdate("ALTER TABLE marca AUTO_INCREMENT = 1;");
                stmt.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al eliminar y reordenar: " + ex.getMessage());
        } finally {
            try { if (cnx != null) cnx.close(); } catch (SQLException ex) {}
        }
        return resultado;
    }
}
