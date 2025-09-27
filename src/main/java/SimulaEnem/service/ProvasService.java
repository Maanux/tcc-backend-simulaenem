package SimulaEnem.service;

import SimulaEnem.domain.prova.Prova;
import SimulaEnem.dto.Prova.ProvasDTO;
import SimulaEnem.repository.ProvasRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProvasService {

    private final ProvasRepository provasRepository;

    public ProvasService(ProvasRepository provasRepository){
        this.provasRepository = provasRepository;
    }

    public List<ProvasDTO> buscarPorUsuarioUuid(UUID usuarioUuid) {
        List<Prova> provas = provasRepository.findByUsuarioExternalId(usuarioUuid);
        return provas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Page<ProvasDTO> buscarPorUsuarioUuid(UUID usuarioUuid, Pageable pageable) {
        Page<Prova> page = provasRepository.findByUsuarioExternalId(usuarioUuid, pageable);
        return page.map(this::toDTO);
    }

    public ProvasDTO buscarPorProvaEUsuario(UUID provaUuid, UUID usuarioUuid) {
        return provasRepository.findByExternalIdAndUsuarioExternalId(provaUuid, usuarioUuid)
                .map(this::toDTO)
                .orElse(null);
    }

    private ProvasDTO toDTO(Prova p) {
        return new ProvasDTO(
                p.getExternalId(),
                p.getTitulo(),
                p.getDataInicio(),
                p.getDataFim(),
                p.getQuestoes() == null ? 0 : p.getQuestoes().size()
        );
    }
}
