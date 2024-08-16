package com.fandresena.learn.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fandresena.learn.model.CalendarDateModel;
import com.fandresena.learn.model.OneDayModel;


@Service
public class CalendarDateService {

    public CalendarDateService(){}
    // retourne 35 jours avec date en JSON
    public CalendarDateModel createCalendarDate(int year, int month) {
        int realMonth = month;
        int realYear = year;
        List<OneDayModel> calendarDays = new ArrayList<>();
        int count = 1;
        LocalDate firsDate = null;
        Month montH = Month.of((month));
        Year yeaR = Year.of(realYear);

        for (int i = 1; i <= 34; i++) {
            if (i == 1) {
                LocalDate date = LocalDate.of(year, month, i);
                // si le premier jour du mois n'est pas dimanche
                if (date.getDayOfWeek().toString() != "SUNDAY") {
                    if (month != 1) {
                        realMonth = month - 1;
                    } else {// si le mois est janvier,on retourne a decembre de l'annnées precedent
                        realMonth = 12;
                        realYear = realYear - 1;
                    }

                    Month monTH = Month.of(realMonth);
                    Year yeAR = Year.of(realYear);

                    for (int j = monTH.length(yeAR.isLeap()) - 7; j <= monTH.length(yeAR.isLeap()); j++) {// voir les
                                                                                                          // jours du
                                                                                                          // mois
                                                                                                          // précedent
                                                                                                          // s'il y a un
                                                                                                          // dimanche
                        LocalDate realDate = LocalDate.of(realYear, realMonth, j);
                        if (realDate.getDayOfWeek().toString() == "SUNDAY") {
                            OneDayModel oneDay = new OneDayModel(realDate.toString(), false);
                            calendarDays.add(oneDay);
                            firsDate = LocalDate.of(realYear, realMonth, j);
                        } else if (firsDate != null && firsDate.isBefore(realDate)) {
                            OneDayModel oneDay = new OneDayModel(realDate.toString(), false);
                            calendarDays.add(oneDay);
                            count++;
                        }

                    }
                    i = count;

                }
            }
            if (i-count+1 <= montH.length(yeaR.isLeap())) {
                LocalDate date = LocalDate.of(year, month, i-count+1);//car le mois d'apres dois commencer par la date 1, mais i n'est plus 1
                OneDayModel oneDay = new OneDayModel(date.toString(), true);
                calendarDays.add(oneDay);
            } else {
                if (month != 12) {
                    realMonth = month + 1;
                } else {// si le mois est decembre,on passe a l'année suivante
                    realMonth = 1;
                    realYear = realYear + 1;
                }
                LocalDate date = LocalDate.of(realYear, realMonth, i-count+1-montH.length(yeaR.isLeap()));//car le mois d'apres dois commencer par la date 1,
                OneDayModel oneDay = new OneDayModel(date.toString(), true);
                calendarDays.add(oneDay);
            }

        }

        CalendarDateModel calendardate = new CalendarDateModel(year, month, calendarDays);

        return calendardate;
    }
}
