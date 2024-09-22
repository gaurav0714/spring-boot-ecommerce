package in.nic.sb_ecom.repositories;

import in.nic.sb_ecom.model.AppRole;
import in.nic.sb_ecom.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
