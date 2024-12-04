public class Town implements Comparable<Town> {

	private String name;

	public Town(String name) {
		this.name = name;

	}

	public Town(Town templateTown) {
		name = templateTown.getName();
	}

	@Override
	public int compareTo(Town o) {
		return name.compareTo(o.getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return getName();
	}

	public int hashCode() {
		return name.toLowerCase().hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Town town = (Town) obj;
		return name.equalsIgnoreCase(town.name);
	}

}
