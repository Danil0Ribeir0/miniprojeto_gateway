package model.repository;

import api.apiStudent;
import model.entity.student;
import model.mapper.studentMapper;

import java.util.List;
import java.util.stream.Collectors;

public class studentRepository extends baseRepository<student> {

    // 1. O mapper deve ser estático. Como ele é stateless, esta é uma boa prática
    // e permite seu uso em métodos estáticos.
    private static final studentMapper MAPPER = new studentMapper();

    public studentRepository(apiStudent apiStudent) {
        // A. A chamada super() DEVE ser a primeira instrução.
        // Chamamos um método estático que executa toda a lógica de carregamento
        // e mapeamento, e retorna a lista para o construtor BaseRepository.
        super(loadAndMapStudents(apiStudent));
    }

    /**
     * Método estático auxiliar para carregar e mapear os dados da API.
     * Deve ser estático pois é chamado antes da construção completa da instância.
     */
    private static List<student> loadAndMapStudents(apiStudent apiStudent) {
        // 1. Acessa o microsserviço
        List<api.dto.studentDTO> dtos = apiStudent.getStudents();

        // 2. Mapeia os DTOs para Entidades de Domínio usando o MAPPER estático
        return dtos.stream()
                .map(MAPPER :: toEntity)
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    @Override
    public student getById(String id) {
        if (id == null) return null;

        // Utilizamos a lista 'entities' que foi populada e está na classe base.
        return entities.stream()
                .filter(s -> id.equals(s.getId()))
                .findFirst()
                .orElse(null);
    }
}