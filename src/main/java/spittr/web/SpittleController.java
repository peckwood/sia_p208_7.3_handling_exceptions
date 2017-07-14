package spittr.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import spittr.Spittle;
import spittr.data.SpittleRepository;
import spittr.exception.DuplicateSpittleException;
import spittr.exception.DuplicateSpittleExceptionGlobal;
import spittr.exception.SpittleNotFoundException;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

  private static final String MAX_LONG_AS_STRING = "9223372036854775807";
  
  private SpittleRepository spittleRepository;

  @Autowired
  public SpittleController(SpittleRepository spittleRepository) {
    this.spittleRepository = spittleRepository;
  }

  @RequestMapping(method=RequestMethod.GET)
  public List<Spittle> spittles(
      @RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
      @RequestParam(value="count", defaultValue="20") int count) {
    return spittleRepository.findSpittles(max, count);
  }

  @RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
  public String spittle(
      @PathVariable("spittleId") long spittleId, 
      Model model) {
	  Spittle spittle = null;
	  if(spittleId==999){
		  //I have to throw a exception like this, or JdbcSpittleRepository's findOne 
		  //method will call JdbcTemplate.queryForObject 
		  //which will throw its own exception upon a null result
		  throw new SpittleNotFoundException(); 
	  }
	  spittle = spittleRepository.findOne(spittleId);
    model.addAttribute(spittle);
    return "spittle";
  }

  @RequestMapping(method=RequestMethod.POST)
  public String saveSpittle(SpittleForm form, Model model) throws Exception {
	if("duplicate".equals(form.getMessage())){
		throw new DuplicateSpittleException();
	}
	if("duplicate-global".equals(form.getMessage())){
		throw new DuplicateSpittleExceptionGlobal();
	}
    spittleRepository.save(new Spittle(null, form.getMessage(), new Date(), 
        form.getLongitude(), form.getLatitude()));
    return "redirect:/spittles";
  }
  
  @ExceptionHandler(DuplicateSpittleException.class)
  public String handleDuplicateSpittle(){
	  return "error/duplicate";
  }
  

}
