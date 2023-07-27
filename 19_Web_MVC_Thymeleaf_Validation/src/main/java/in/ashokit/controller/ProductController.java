package in.ashokit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.entity.Product;
import in.ashokit.repo.ProductRepo;

@Controller
public class ProductController {
	
	@Autowired
	private ProductRepo repo;
	
	@GetMapping("/products")
	public String getAllProducts(Model model) {
			List<Product> list = repo.findAll();
			model.addAttribute("products",list);
		return "data";
	}
	
	@GetMapping("/")
	public String loadForm(Model model) {
		
		model.addAttribute("product",new Product());
		return "index";
	}
	@PostMapping("/product")
	//BindingResult obj will tell the validation are failed or not failed
	//@Validated it is used to validate binding class obj data with validation rules
	public String saveProduct(@Validated @ModelAttribute("product") Product p,BindingResult result ,Model model) {
		
		if(result.hasErrors()) {
			System.out.println(p);
			return "index";
		}
		
		
		 p= repo.save(p);
		 
		 if(p.getPid()!=null) {
			 model.addAttribute("msg","Product Saved...");
		 }
	
		return "index";
		
	}
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam("pid") Integer pid, Model model) {
		
		repo.deleteById(pid);
		model.addAttribute("msg","Product Deleted...");
		List<Product> list = repo.findAll();
		model.addAttribute("products",list);
		
		return "data";
		
	}
	@GetMapping("/edit")
	public String editById(@RequestParam("pid") Integer pid,Model model) {
		Optional<Product> findById = repo.findById(pid);
		
		if(findById.isPresent()) {
			Product product = findById.get();
			model.addAttribute("product",product);
		}
		
		return "index";
		
	}

}
