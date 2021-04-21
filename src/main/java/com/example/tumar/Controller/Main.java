package com.example.tumar.Controller;

import com.example.tumar.Model.Weapon;
import com.example.tumar.Repo.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Optional;

@EnableSwagger2
@Controller
public class Main {

    @Autowired
    private WeaponRepository weaponRepository;

    @GetMapping("/")
    public String main(Model model){
        Iterable<Weapon> weapons = weaponRepository.findAll();
        Iterable<Weapon> carousel = weaponRepository.findAllByType("Carousel");
        model.addAttribute("weapons",weapons);
        model.addAttribute("carousel",carousel);
        return "Main";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }
    @GetMapping("/buy")
    public String buy(){
        return "buy";
    }

    @GetMapping("/closeCombat")
    public String getCloseCombat(Model model){
        Iterable<Weapon> weapons = weaponRepository.findAllByType("Close Combat");
        model.addAttribute("weapons",weapons);
        return "closeCombat";
    }

    @GetMapping("/distantCombat")
    public String getDistantCombat(Model model){
        Iterable<Weapon> weapons = weaponRepository.findAllByType("Distant Combat");
        model.addAttribute("weapons",weapons);
        return "closeCombat";
    }

    @GetMapping("/weapon-add")
    public String weaponAdd(){
        return "weapon-add";
    }

    @PostMapping("/weapon-add")
    public String weaponNewAdd(@RequestParam String name, @RequestParam String price,
                                @RequestParam String info, @RequestParam String imageUrl, @RequestParam String types, Model model){
        Weapon weapon = new Weapon(name,price,info,imageUrl,types);
        weaponRepository.save(weapon);
        return "redirect:/";
    }

    @GetMapping("/weapon/{id}/edit")
    public String weaponEdit(@PathVariable(value = "id") long id, Model model) {
        if(!weaponRepository.existsById(id)) {
            return "redirect:/";
        }

        Optional<Weapon> weapon = weaponRepository.findById(id);
        ArrayList<Weapon> res = new ArrayList<>();
        weapon.ifPresent(res::add);
        model.addAttribute("weapon", res);
        return "weapon-edit";
    }

    @PostMapping("/weapon/{id}/edit")
    public  String weaponPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String price, @RequestParam String url, Model model){
        Weapon weapon = weaponRepository.findById(id).orElseThrow();
        weapon.setName(name);
        weapon.setPrice(price);
        weapon.setImageUrl(url);
        weaponRepository.save(weapon);
        return  "redirect:/";

    }

    @PostMapping("/weapon/{id}/delete")
    public String weaponDelete(@PathVariable(value = "id") long id){
        Weapon weapon = weaponRepository.findById(id).orElseThrow();
        weaponRepository.delete(weapon);
        return "redirect:/";
    }

    @GetMapping("/weapon/{id}")
    public String weaponDetails(@PathVariable(value = "id") long id, Model model){
        if(!weaponRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Weapon> product = weaponRepository.findById(id);
        ArrayList<Weapon> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("weapon",res);
        return "weapon-detail";
    }
}
