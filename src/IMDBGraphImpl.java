import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IMDBGraphImpl implements IMDBGraph{
	private Map<String, IMDBNode> _movies;
	private Map<String, IMDBNode> _actors;

	public IMDBGraphImpl (String actorsFilename, String actressesFilename) throws IOException {
		_movies = new HashMap<String, IMDBNode>();
		_actors = new HashMap<String, IMDBNode>();
		parse(actorsFilename);
		parse(actressesFilename);
	}
	@Override
	public Collection<? extends Node> getActors() {
		return _actors.values();
	}

	@Override
	public Collection<? extends Node> getMovies() {
		return _movies.values();
	}

	@Override
	public Node getMovie(String name) {
		return _movies.get(name);
	}

	@Override
	public Node getActor(String name) {
		return _actors.get(name);
	}
	
	private void parse(String file) throws IOException {
		final Scanner actorScan = new Scanner(new File(file), "ISO-8859-1");
		boolean startScanning = false;
		while (actorScan.hasNextLine()) {
			String line = actorScan.nextLine();
			if(!startScanning && line.equals("----			------")) {
				startScanning = true;
			}
			if(startScanning) {
				if(line.indexOf(" ") > 0){
					int blankSpace = line.indexOf("	");
					String actName = line.substring(0, blankSpace);
					if(!actName.contains("	")) {
						_actors.put(actName, new IMDBNode(actName));
					}
					int endMovie = line.indexOf(")");
					if(endMovie != -1) {
						String movieName = line.substring(blankSpace, endMovie).trim().concat(")");
						if(!_movies.containsKey(movieName)) {
							_movies.put(movieName, new IMDBNode(movieName));
						}
					}
				}
			}
		} 
		actorScan.close();
	}
	

}
