package co.com.arch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.arch.entidades.Usuarios;

/**
 * 
 * @author diarchila
 *	{@literal Esta Interface hace los CRUDS, de la Tabla usuarios}
 */
public interface UsuariosDAO extends JpaRepository<Usuarios, Long> {

}
