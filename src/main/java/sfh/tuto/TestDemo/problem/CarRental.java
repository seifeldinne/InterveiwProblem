package sfh.tuto.TestDemo.problem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CarRental {
	public static final SimpleDateFormat sdf = new SimpleDateFormat("d/M/y H:m");

	public static Boolean canScheduleAllWithBefore(Collection<RentalTime> rentalTimes) {
		long start = System.currentTimeMillis();
		for (RentalTime rs : rentalTimes) {
			boolean result = rentalTimes.parallelStream().filter(rt -> !rt.equals(rs))
					.anyMatch(re -> rs.getStart().before(re.getStart()) && rs.getEnd().after(re.getStart())
							|| rs.getStart().before(re.getEnd()) && rs.getEnd().after(re.getEnd())
							|| rs.getStart().before(re.getStart()) && rs.getEnd().after(re.getEnd())
							|| rs.getStart().after(re.getStart()) && rs.getEnd().before(re.getEnd()));

			if (result) {
				System.out.println(rs);
				return false;
			}

		}
		System.out.println(System.currentTimeMillis() - start);
		return true;
	}

	public static Boolean canScheduleAllWithCompareTo(Collection<RentalTime> rentalTimes) {
		long start = System.currentTimeMillis();
		for (RentalTime rs : rentalTimes) {
			boolean result = rentalTimes.stream()
					.filter(rt -> !rt.equals(rs))
					.anyMatch(re -> rs.getStart().compareTo(rs.getEnd()) < 0 && re.getStart().compareTo(rs.getEnd()) > 0
							|| rs.getStart().compareTo(re.getEnd()) < 0 && re.getStart().compareTo(re.getEnd()) > 0
							|| rs.getStart().compareTo(rs.getEnd()) < 0 && re.getStart().compareTo(re.getEnd()) > 0
							|| rs.getStart().compareTo(rs.getEnd()) > 0 && re.getStart().compareTo(re.getEnd()) < 0);

			if (result) {
				for (RentalTime rentalTime : rentalTimes) {
					if (rentalTime.equals(rs))
						System.out.println(rentalTime);
				}
				return false;
			}
		}
		System.out.println(System.currentTimeMillis() - start);
		return true;
	}

	static short second = 1000;
	static byte minute = 60;
	static byte hour = 24;
	static long day = second * minute * hour;

	public static void main(String[] args) throws Exception {
		List<RentalTime> rentalTimes = generate(50000);
		/*
		 * System.out.println(d); System.out.println(dd);
		 * rentalTimes.stream().forEach(System.out::println);
		 */

		System.out.println(CarRental.canScheduleAllWithBefore(rentalTimes));
		System.out.println(CarRental.canScheduleAllWithCompareTo(rentalTimes));
	}

	public static List<RentalTime> generate(int size) {
		List<RentalTime> rentalTimes = new ArrayList<>();
		Date ds = new Date();

		long time = ds.getTime();

		Date de;
		for (int i = 0; i < size; i++) {
			ds = new Date(time);
			de = new Date(time + hour);
			time += day;
			try {
				rentalTimes.add(new RentalTime(sdf.parse(sdf.format(ds)), sdf.parse(sdf.format(de))));
			} catch (ParseException e) {
			}
		}
		return rentalTimes;
	}
}

class RentalTime {
	private Date start, end;

	public RentalTime(Date start, Date end) {
		this.start = start;
		this.end = end;
	}

	public Date getStart() {
		return this.start;
	}

	public Date getEnd() {
		return this.end;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RentalTime [\nstart=");
		builder.append(CarRental.sdf.format(start));
		builder.append(", \nend  =");
		builder.append(CarRental.sdf.format(end));
		builder.append("\n]");
		return builder.toString();
	}

}