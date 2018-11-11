import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IMDBGraphImpl implements IMDBGraph{
	final private Map<String, IMDBNode> _movies;
	final private Map<String, IMDBNode> _actors;

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
		final File scan = new File(file);
		final Scanner actorScan = new Scanner(scan, "ISO-8859-1");
		boolean startScanning = false;
		String lastActor = "";
		while (actorScan.hasNextLine()) {
			String line = actorScan.nextLine();
			if(!startScanning && line.equals("----			------")) {
				startScanning = true;
			}
			if(startScanning) {
				if(line.equals("-----------------------------------------------------------------------------")) {
					actorScan.close();
					break;
				}
				else if(line.indexOf(" ") > 0){
					final int blankSpace = line.indexOf("	");
					final String actName = line.substring(0, blankSpace); 
					if(blankSpace != 0 && !actName.contains("	") && actName.length() != 0) {
						lastActor = actName.trim();
					}
					final boolean TV = line.contains("TV") || line.contains("\"");
					final int endMovie = findYearIndex(line);
					if(endMovie != -1 && !TV) {
						if (!_actors.containsKey(actName)) {
							_actors.put(actName, new IMDBNode(actName));
						}
						if(!_actors.containsKey(lastActor)) {
							_actors.put(lastActor, new IMDBNode(lastActor));
						}
						String movieName = line.substring(blankSpace, endMovie).trim().concat(")");
						if(!_movies.containsKey(movieName)) {
							_movies.put(movieName, new IMDBNode(movieName));
						}
						if(movieName.equals("")) {
							_movies.remove(movieName);
						}
						else {
							_movies.get(movieName).addEdge(_actors.get(lastActor));
							_actors.get(lastActor).addEdge(_movies.get(movieName));
						}
					}
					if(actName.equals("")) {
						_actors.remove(actName);
					}
				}
			}
		} 
		actorScan.close();
	}
	
	private int findYearIndex(String x) {
		for(int i = 1; i < x.length(); i++) {
			if(x.substring(i, i+1).equals(")") && Character.isDigit(x.charAt(i-1))) return i;
		}
		return -1;
	}
	

}
