package rm.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rm.backend.dto.innerDto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    public Category(CategoryDto.CreateRequest request){
        name = request.getName();
        parent = null;
    }

    public Category(CategoryDto.CreateRequest request, Category parentCategory){
        name = request.getName();
        parent = parentCategory;
    }


    public void update(CategoryDto.UpdateRequest request, Category parentCategory){
        name = request.getName();
        parent = parentCategory;
    }
}
