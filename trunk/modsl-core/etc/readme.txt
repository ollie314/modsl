### TODO

graphviz
http://www.umlgraph.org/

godaddy hosting http://help.godaddy.com/article/1668
shadow using gradient?
mirror/flip diagram based on x, y sizes
cl diagram add note
cl d abstract (italics)
cl d additional connector decorations for aggregation
template class diagram notation
baricenter, disconnected graphs
separate styleesheet + cmd
collaboration diagram
rescale before fr layout
test on padding
rotate for low weight?
horizontal bars for the FR algorithm
weights for FR algorithm
padding of the diagram when rendering
font metrics vs dpi, serif is off base
IE compat
GEM, including random vertex selection, baricenter, local temp, skew, rot/oscillation detection
limit time + number of iterations
add version to diagram
FR - try to fix tangling by changing forces
website - submit online, store, expire. serve through PHP/Apache?
triangle diagram
blocky diagram
slides
diff for online users
separate properties for layout, different kinds of layout, read *.properties form the folder?
common resources for svg (styles etc), use references
rescale on every fr layout iteration?
ANTLR
collaboration diagram

### DONE

* make diagram objects responsible for calclating sizes/positions, pass config in
x use objectBoundingBox for scaling?
* static vs member, public etc
* correct text handling -- baseline, ascent descent etc
* xy coordinates - double check adjusted connector since Y is reverted, the same for circling the vertexes
* document lead->gap->trail
* official publishing terms (to replace lead, trail)
* command line utility
* reading resources from jar file
* transform by flipping 90, 180, 270 degrees to bring lowest weight vertexes on top
* caching of templates
* diagram size parameters
* colors, line thikness attributes, filling
* real font metrics
* real object model, translate attributes into groovy temp bindings transparently
* subclass for different kinds of diagrams
* pass whole object to template in bindings
* profiling
* stop caching size
* optimize XY method usage
* turn history on and off
* fr config into property file
* style vs individual attribues in svg, customization/properties
* diagram name
x partition in maven
* in layout take element size into account
x recalc diagram size before layout?
* decorations on arrows
* gradient
* initial placement on a circle/multiple circles (by number of edges)? sim annealing/adge length?
* filled polygon arrows
* common class for font-size based configuration
* optimize getWeight on vertex
* background color property
* render history first
* fill rect behind connector captions
* apache file headers
x template merge for performance
* class diagram separate attributes from methods
x SDL - simple descriptive language

### ALGORITHMS

Fruchterman Rheingold graph
JUNG
http://www.csse.monash.edu.au/~berndm/CSE460/Lectures/cse460-7.pdf
http://www.ddj.com/architect/184411016

### SEQ

Read input (builder, DSL)

Build diagram model

Calculate primitives's size

Layout
	Layout manager acts on
		Graph which consist of
			Vertexes
			Edges
		Configured through Config 
		
Render as SVG

Utils
	Tmpl
	QuickSvg
	
	
Will need to support 
	element-connector-element
	standalone element
	header
	note
	connector
		name
		left cardinality
		right cardinality
	primitive inside primitive
	baselines for alignment
	weights (superclasses bubble up, subclasses sink down)