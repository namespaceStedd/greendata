package alex.greendata.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationFormRepository extends JpaRepository<OrganizationForm, Object> {
}
