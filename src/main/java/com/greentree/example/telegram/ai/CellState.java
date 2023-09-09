package com.greentree.example.telegram.ai;

public enum CellState {
    X {
        @Override
        public CellState toInverse() {
            return O;
        }
    }, O {
        @Override
        public CellState toInverse() {
            return X;
        }
    }, Empty {
        @Override
        public CellState toInverse() {
            throw new UnsupportedOperationException("CellState.Empty.toInverse()");
        }
    };

    public abstract CellState toInverse();

}
