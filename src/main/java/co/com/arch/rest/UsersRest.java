package co.com.arch.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.arch.dao.UsuariosDAO;
import co.com.arch.entidades.Usuarios;

@RestController
@RequestMapping("/usuarios")
public class UsersRest {
	
	
	/**
	 * @AutoWire
	 */
	@Autowired
	private UsuariosDAO usuariosDAO;
	
	
	/**
	 *  Función para obtener todos los usuarios, de la tabla usuarios.
	 *  @GetMapping por defecto en la Ruta /usuarios.
	 * @return regresa un tipo {@link ResponseEntity}, con el resultado 
	 *  de la función {@link UsuariosDAO.findAll()}
	 */
	@GetMapping
	public ResponseEntity<List<Usuarios>> getUsers(){
		
		List<Usuarios> usuarios= usuariosDAO.findAll();
		
		return ResponseEntity.ok(usuarios);
	}
	
	
	/**
	 *  Función para obtener un usuario por su ID, de la tabla usuarios.
	 *  @param usuarioID ID del usuario a consultar.
	 *  @RequestMapping por defecto en la Ruta /usuarios/{@code {usuarioID}
	 * @return regresa un tipo {@link ResponseEntity}, con el resultado 
	 *  de la función {@code usuarioDAO.findById(usuarioID)}
	 */
	@RequestMapping(value= "{usuarioID}")
	public ResponseEntity<Usuarios> getUserByID(@PathVariable("usuarioID") Long usuarioID){
		
		/*
		 * Se crea un tipo Optional de Usuarios para validarlo.
		 * 
		 */
		Optional<Usuarios> optionalusuario= usuariosDAO.findById(usuarioID);
		
		if(optionalusuario.isPresent()) {
			return ResponseEntity.ok(optionalusuario.get());
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping //			/usuarios (POST)
	public ResponseEntity<Usuarios> createUser(@RequestBody Usuarios usuario){
				Usuarios newUsuario= usuariosDAO.save(usuario);
		return ResponseEntity.ok(newUsuario);
	}
	
	
	@DeleteMapping(value="{usuarioID}") //			/usuarios (DELETE)
	public ResponseEntity<Usuarios> deleteUser(@PathVariable("usuarioID") Long usuarioID){
		usuariosDAO.deleteById(usuarioID);
		return ResponseEntity.ok(null);
	}
	
	
	@PutMapping
	public ResponseEntity<Usuarios> updateUser(@RequestBody Usuarios usuarioID) {
		
		Optional<Usuarios> optionalusuario= usuariosDAO.findById(usuarioID.getId());
		
		if(optionalusuario.isPresent()) {
			
			Usuarios updateUsuario = optionalusuario.get();
			
			System.out.println(optionalusuario.get());
			
			updateUsuario.setNombre(usuarioID.getNombre());
			updateUsuario.setGenero(usuarioID.getGenero());
			updateUsuario.setFechanacimiento(usuarioID.getFechanacimiento());
			updateUsuario.setTelefono(usuarioID.getTelefono());
			updateUsuario.setEmail(usuarioID.getEmail());
			
			usuariosDAO.save(updateUsuario);
			
			return ResponseEntity.ok(updateUsuario);
			
		}else {
			return ResponseEntity.notFound().build();
		}		
	}

}

