package ControleFacil_Backend.models.recibos;

import ControleFacil_Backend.models.Usuario;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recibos_anuais")
public class RecibosAnuais {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "recibos_anuais_id", nullable = false)
    private List<Recibo> recibosAnuais;
}
