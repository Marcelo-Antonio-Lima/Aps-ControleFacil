package ControleFacil_Backend.services;

import ControleFacil_Backend.models.Grupo;
import ControleFacil_Backend.models.Usuario;
import ControleFacil_Backend.repositories.GrupoRepository;
import ControleFacil_Backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Grupo criarGrupo(Grupo grupo) {
        if (grupo.getCriadorGrupo() == null || grupo.getCriadorGrupo().getId() == null) {
            throw new IllegalArgumentException("Criador do grupo é obrigatório");
        }
        return grupoRepository.save(grupo);
    }

    @Transactional(readOnly = true)
    public Optional<Grupo> buscarPorId(Long id) {
        return grupoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Grupo> listarTodos() {
        return grupoRepository.findAll();
    }

    public Grupo atualizarGrupo(Grupo grupo) {
        if (grupo.getId() == null || !grupoRepository.existsById(grupo.getId())) {
            throw new IllegalArgumentException("Grupo não encontrado");
        }
        return grupoRepository.save(grupo);
    }

    public void removerGrupo(Long id) {
        if (!grupoRepository.existsById(id)) {
            throw new IllegalArgumentException("Grupo não encontrado");
        }
        grupoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Grupo> listarPorCriador(Long criadorId) {
        Usuario criador = usuarioRepository.findById(criadorId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário criador não encontrado"));
        return grupoRepository.findByCriadorGrupo(criador);
    }

    @Transactional(readOnly = true)
    public List<Grupo> listarPorMembro(Long usuarioId) {
        return grupoRepository.findByMembrosGrupo_Id(usuarioId);
    }
}


