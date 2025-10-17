package ControleFacil_Backend.repositories;

import ControleFacil_Backend.models.Grupo;
import ControleFacil_Backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findByCriadorGrupo(Usuario criadorGrupo);
    List<Grupo> findByMembrosGrupo_Id(Long usuarioId);
}


