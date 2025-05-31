import com.gymmaster.dao.PistaDao;
import com.gymmaster.models.Pista;
import com.gymmaster.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pistas")
public class PistaController {

    @Autowired
    private PistaDao pistaDao;

    @GetMapping
    public List<Pista> getTodas() {
        return pistaDao.getTodas();
    }

    @PostMapping
    public void guardar(@RequestBody Pista pista) {
        pistaDao.guardar(pista);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pistaDao.eliminar(id);
    }
}
