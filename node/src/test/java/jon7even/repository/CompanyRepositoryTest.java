package jon7even.repository;

import com.github.jon7even.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CompanyRepositoryTest extends GenericRepositoryTest {
    @Autowired
    private CompanyRepository companyRepository;
}
