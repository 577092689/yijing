package org.boc.calendar.ui;
import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

import org.boc.util.Public;

/**
 * ѡ�еļ�¼�뵱ǰ��¼ȫ���ǹ���������û��ũ����Ҳ����Ҫũ��
 */
public class CalendarTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private int currentYear;
	private int currentMonth;
	private int currentDay;
	private int currentHour;
	private int currentMinute;
	private int selectYear;
	private int selectMonth;
	private int selectDay;
	private int selectHour;
	private int selectMinute;
	
	private Calendar clr;

	public CalendarTableModel() {
		clr = Public.getCalendarForBeijing();
		this.currentYear = this.selectYear = clr.get(Calendar.YEAR);
		this.currentMonth = this.selectMonth = clr.get(Calendar.MONTH);
		this.currentDay = this.selectDay = clr.get(Calendar.DAY_OF_MONTH);
		this.currentHour = this.selectHour = clr.get(Calendar.HOUR_OF_DAY);
		this.currentMinute = this.selectMinute = clr.get(Calendar.MINUTE);
		//System.out.println(this.currentHour+":"+currentMinute);
		this.update();
	}
	
	public int getSelectYear() {
		return selectYear;
	}
	public void setSelectYear(int selectYear) {
		this.selectYear = selectYear;
		this.clr.set(Calendar.YEAR, this.selectYear);
		this.update();
	}
	public int getSelectDay() {
		if (this.selectDay > this.maxDay) this.selectDay = this.maxDay;
		return this.selectDay;
	}
	public void setSelectDay(int selectDay) {
		if (selectDay > this.maxDay) selectDay = this.maxDay;
		this.selectDay = selectDay;
		this.clr.set(Calendar.DAY_OF_MONTH, this.selectDay);
	}
	public int getSelectMonth() {
		return selectMonth;
	}
	public void setSelectMonth(int selectMonth) {
		this.selectMonth = selectMonth;
		this.clr.set(Calendar.MONTH, this.selectMonth);
		this.update();
	}
	public int getCurrentYear() {
		return currentYear;
	}
	public int getCurrentDay() {
		return currentDay;
	}
	public int getCurrentMonth() {
		return currentMonth;
	}
	//�ꡢ�¡���֮���Կ���һ�ι����ظ�ʹ�ã����ڲ�������һ�첻�رգ���Сʱ������������
	public int getCurrentHour() {
		clr = Public.getCalendarForBeijing();
		return this.currentHour = this.selectHour = clr.get(Calendar.HOUR_OF_DAY);
	}
	public int getCurrentMinute() {
		clr = Public.getCalendarForBeijing();
		return this.currentMinute = this.selectMinute = clr.get(Calendar.MINUTE);
	}
	private void update() {
		clr.set(Calendar.DAY_OF_MONTH, 1);
		this.firstDayOfWeek = clr.get(Calendar.DAY_OF_WEEK);
		this.maxDay = clr.getActualMaximum(Calendar.DAY_OF_MONTH);
		this.maxWeek = clr.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}
	private int maxDay;
	private int maxWeek;
	private int firstDayOfWeek;
	public Object getValueAt(int row, int col) {
		int t = this.mapValue(row, col);
		if (t>0) {
			if (this.currentMonth == this.selectMonth
					&& this.currentYear == this.selectYear
					&& t == this.currentDay)
				return "<html><b><font color='blue'>" + t + "</font></b></html>";
			return String.valueOf(t);
		}
		return "";
	}
	public int mapValue(int row, int col) {
		int t = 7 * row + col + 2 - this.firstDayOfWeek;
		if (t>0 && t<=this.maxDay) {
			return t;
		}
		return -1;
	}
	public int getRowCount() {
		return this.maxWeek;
	}
	
	public int getColumnCount() {
		return 7;
	}
	
	final String[] titles = { "��", "һ", "��", "��", "��", "��", "��" };
	public String getColumnName(int column) {
		return titles[column];
	}
	public boolean isCellEditable(int row, int column){
		return false;
	}
}