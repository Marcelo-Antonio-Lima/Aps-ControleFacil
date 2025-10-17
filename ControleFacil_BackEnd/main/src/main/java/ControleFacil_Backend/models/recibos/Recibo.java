package ControleFacil_Backend.models.recibos;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recibos")
public class Recibo {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_recibo", nullable = false)
    private String nomeRecibo;

    @Column(name = "descricao_recibo", nullable = true)
    private String descricaoRecibo;

    @Column(name = "codigo_recibo", nullable = false)
    private String codigoRecibo;

    @Column(name = "valor_recibo", nullable = false)
    private Double valorRecibo;

    @Column(name = "data_recebimento", nullable = false)
    private LocalDateTime dataRecebimento;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;




}
