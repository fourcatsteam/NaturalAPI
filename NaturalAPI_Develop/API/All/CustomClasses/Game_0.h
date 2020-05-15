class Game {
private:
	int maxPlayers;
public:
	void setmaxPlayers ("attribute_type maxPlayers") {
		this.maxPlayers = maxPlayers;
	}

	int getmaxPlayers () const {
		return maxPlayers
	}
private:
	float price;
public:
	void setprice ("attribute_type price") {
		this.price = price;
	}

	float getprice () const {
		return price
	}
private:
	string name;
public:
	void setname ("attribute_type name") {
		this.name = name;
	}

	string getname () const {
		return name
	}
}