package ControleFacil_Backend.models.recibos;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recibos_mensais")
public class RecibosMensais {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_mes", nullable = false)
    private LocalDateTime dataMes;    

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "recibos_mensais_id", nullable = false)
    private List<Recibo> recibosMensais;

    private  RecibosAnuais recibosAnuais;

    
    
}
