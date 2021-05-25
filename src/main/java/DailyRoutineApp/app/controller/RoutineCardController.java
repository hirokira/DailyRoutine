package DailyRoutineApp.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import DailyRoutineApp.app.entity.D_Routine;
import DailyRoutineApp.app.service.D_RoutineService;

@Controller
public class RoutineCardController {


	@Autowired
	private D_RoutineService d_service;

	@RequestMapping(value="/card",method=RequestMethod.GET)
	public ModelAndView card(ModelAndView mav) {
		mav.setViewName("card");
		List<D_Routine> list = d_service.findAll();
		mav.addObject("list", list);
		return mav;
	}

}
