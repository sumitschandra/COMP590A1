package main;

public class InternalHuffmanNode implements HuffmanNode {

	private HuffmanNode _left, _right;

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return this._left.count() + this._right.count();
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int symbol() throws Exception {
		// TODO Auto-generated method stub
		throw new RuntimeException("Internal nodes have no symbol.");
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return Math.max(this._right.height() + 1, this._left.height() + 1);

	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		if (this._right == null) {
			return false;
		}

		if (this._left == null) {
			return false;
		} else {
			return this._right.isFull() && this._left.isFull();
		}
	}

	@Override
	public boolean insertSymbol(int length, int symbol) throws Exception {
		if (_left != null) {

			if (_right != null) {
				if (!_right.isFull()) {
					return _right.insertSymbol(length - 1, symbol);
				}
				if (!_left.isFull()) {
					return _left.insertSymbol(length - 1, symbol);
				} else {
					return false;
				}
			} else {
				if (length != 1) {
					_right = new InternalHuffmanNode();
					return _right.insertSymbol(length - 1, symbol);
				}
				if (length == 1) {
					_right = new LeafHuffmanNode(symbol, 0);
					return true;
				}
			}
		} else {
			if (length != 1) {
				_left = new InternalHuffmanNode();
				return _left.insertSymbol(length - 1, symbol);
			}
			if (length == 1) {
				_left = new LeafHuffmanNode(symbol, 0);
				return true;
			}

		}
		return false;
	}

	@Override
	public HuffmanNode left() throws Exception {
		// TODO Auto-generated method stub
		return _left;
	}

	@Override
	public HuffmanNode right() throws Exception {
		// TODO Auto-generated method stub
		return _right;
	}

	public InternalHuffmanNode(HuffmanNode l, HuffmanNode r) {
		_left = l;
		_right = r;
	}

	public InternalHuffmanNode() {
		_left = null;
		_right = null;
	}

}
